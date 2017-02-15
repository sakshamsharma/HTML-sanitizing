import akka.http.scaladsl.server.Directives._
import akka.actor.ActorSystem
import akka.stream.Materializer
import scala.concurrent.ExecutionContextExecutor
import com.typesafe.config.Config
import akka.event.LoggingAdapter

import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

case class SanitizeReq (body: String)

trait JsonProtocols extends DefaultJsonProtocol {
  implicit val sanitizeReq = jsonFormat1(SanitizeReq.apply)
}

trait HttpRouter extends JsonProtocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter

  val routes = {
    logRequestResult("html-sanitizing-microservice") {

      (get & pathPrefix("health")) {
        complete("Hello from the other side!")
      } ~
      pathPrefix("sanitize") {
        (post & entity(as[SanitizeReq])) { reqData =>
          complete(Sanitizer(reqData.body))
        }
      }
    }
  }
}
