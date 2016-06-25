package actors

import java.util

import akka.actor._
import minesweeper.aview.messages.{LoginResponse, NewAccountResponse, UpdateMessage}
import minesweeper.controller.impl.DependencyInjectorMainController
import minesweeper.controller.messages.MainController._
import minesweeper.controller.messages.{RedoRequest, RevealCellRequest, UndoRequest}
import minesweeper.util.JsonConverter
import play.api.libs.json._

object WebSocketActor {
    def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {

    val controller = context.actorOf(Props.create(classOf[DependencyInjectorMainController]), "controller")
    controller.tell(new UpdateRequest(), self)

    def receive = {
        case msg: String => handleClientMessage(msg)
        case response: UpdateMessage => handleUpdateMessage(response)
        case response: LoginResponse => handleLoginResponse(response)
        case response: NewAccountResponse => handleNewAccountResponse(response)
    }

    def handleLogin(json: JsValue): Unit = {
        val username = (json \ "username").as[String]
        val pass = (json \ "pass").as[String]

        controller.tell(new LoginRequest(username, pass), self)
    }

    def handlerRegister(json: JsValue): Unit = {
        val username = (json \ "username").as[String]
        val pass = (json \ "pass").as[String]

        controller.tell(new NewAccountRequest(username, pass), self)
    }

    def handleCommand(json: JsValue): Unit = {
        val command = (json \ "command").as[String]
        val revealFieldPattern = "([0-9][0-9]-[0-9][0-9])".r
        command match {
            case "n" => controller.tell(new NewGameRequest, self)
            case "u" => controller.tell(new UndoRequest, self)
            case "r" => controller.tell(new RedoRequest, self)
            case "sS" => controller.tell(new NewSizeRequest(9,9,10), self)
            case "sM" => controller.tell(new NewSizeRequest(16,16,40), self)
            case "sL" => controller.tell(new NewSizeRequest(30,30,99), self)
            case revealFieldPattern(c) => controller.tell(new RevealCellRequest(command.split('-')(0).toInt,
                command.split('-')(1).toInt), self)
        }
    }

    def handleClientMessage(msg: String): Unit = {
        val json = Json.parse(msg)
        (json \ "type").as[String] match {
            case "login" => handleLogin(json)
            case "register" => handlerRegister(json)
            case "command" => handleCommand(json)
        }
    }

    def handleUpdateMessage(response: UpdateMessage): Unit = {
        val jsonString = JsonConverter.createJsonFromMultiArray(response.getField)
        out ! jsonString
    }

    def handleLoginResponse(response: LoginResponse): Unit = {
        val json: JsValue = Json.obj(
            "type" -> "LoginResponse",
            "success" -> response.isSuccess,
            "user" -> response.getUser
        )
        out ! Json.stringify(json)
    }

    def handleNewAccountResponse(response: NewAccountResponse): Unit = {
        val json: JsValue = Json.obj(
            "type" -> "RegisterResponse",
            "success" -> response.getSuccess
        )
        out ! Json.stringify(json)
    }

}
