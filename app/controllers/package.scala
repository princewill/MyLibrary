import play.api.libs.json.{JsError, JsSuccess, Reads}
import play.api.mvc.{BodyParser, ControllerComponents}

import scala.concurrent.ExecutionContext

package object controllers {
  implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.global

  def jsonBodyParser[T](implicit reads: Reads[T], controllerComponents: ControllerComponents): BodyParser[T] =
    controllerComponents.parsers.tolerantJson.map { json =>
      json.validate[T] match {
        case JsSuccess(value, _) => value
        case jsError: JsError => throw new Exception(jsError.toString)
      }
    }
}
