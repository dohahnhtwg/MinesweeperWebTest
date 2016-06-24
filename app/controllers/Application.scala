package controllers

import actors.WebSocketActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.Inject
import play.api.libs.streams.ActorFlow
import play.api.mvc._

class Application @Inject() (implicit system: ActorSystem, materializer: Materializer) extends Controller {

  def index = Action {
    Ok(views.html.index("Minesweeper"))
  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => WebSocketActor.props(out))
  }
}