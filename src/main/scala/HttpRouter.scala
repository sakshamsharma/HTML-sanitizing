import akka.http.scaladsl.server.Directives._
import akka.actor.ActorSystem
import akka.stream.Materializer
import scala.concurrent.ExecutionContextExecutor
import com.typesafe.config.Config
import akka.event.LoggingAdapter

import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import org.owasp.html.HtmlPolicyBuilder

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

  def sanitize(untrustedHTML: String): String = {

    val policy = new HtmlPolicyBuilder()
      .allowElements("a")
      .allowUrlProtocols("https")
      .allowAttributes("href").onElements("a")
      .requireRelNofollowOnLinks()
      .toFactory();

    return policy.sanitize(untrustedHTML);
  }

  val routes = {
    logRequestResult("html-sanitizing-microservice") {

      (get & pathPrefix("health")) {
        complete("Hello from the other side!")
      } ~ pathPrefix("sanitize") {
        (post & entity(as[SanitizeReq])) { body =>
          complete(sanitize(body.body))
        }
      }
    }
  }
}
