package actors

import akka.actor._
import minesweeper.aview.messages.{LoginResponse, NewAccountResponse, UpdateMessage}
import minesweeper.controller.impl.DependencyInjectorMainController
import minesweeper.controller.messages.MainController.{LoginRequest, NewAccountRequest, UpdateRequest}
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

    def handleClientMessage(msg: String): Unit = {
        val json = Json.parse(msg)
        (json \ "type").as[String] match {
            case "login" => handleLogin(json)
            case "register" => handlerRegister(json)
        }
    }

    def handleUpdateMessage(response: UpdateMessage): Unit = {
        val json: JsValue = Json.obj(
            "type" -> "UpdateMessage",
            "field" -> response.getField.toString
        )
        out ! Json.stringify(json)
    }

    def handleLoginResponse(response: LoginResponse): Unit = {
        val json: JsValue = Json.obj(
            "type" -> "LoginResponse",
            "success" -> response.isSuccess
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
