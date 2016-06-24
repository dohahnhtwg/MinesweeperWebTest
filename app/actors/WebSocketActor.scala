package actors

import akka.actor._
import minesweeper.aview.messages.UpdateMessage
import minesweeper.controller.impl.DependencyInjectorMainController
import minesweeper.controller.messages.MainController.UpdateRequest

object WebSocketActor {
    def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {

    val controller = context.actorOf(Props.create(classOf[DependencyInjectorMainController]), "controller")

    def receive = {
        case msg: String =>
            out ! ("I received your message: " + msg)
            controller.tell(new UpdateRequest(), self)
        case reponse: UpdateMessage => out ! reponse.getField.toString
    }
}
