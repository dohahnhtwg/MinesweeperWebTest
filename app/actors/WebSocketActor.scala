package actors

import akka.actor._

object WebSocketActor {
    def props(out: ActorRef) = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor {

    //val controller = context.actorOf(Props.create(classOf[DependencyInjectorMainController], "controller"))

    def receive = {
        case msg: String =>
            out ! ("I received your message: " + msg)
    }
}
