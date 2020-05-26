package controllers

import play.api.libs.json.{JsError, JsSuccess, Reads}
import play.api.mvc.{BodyParser, ControllerComponents}

import scala.concurrent.ExecutionContext

trait ControllerHelper {

  //TODO create better exception handler
  def jsonBodyParser[T](implicit reads: Reads[T], controllerComponents: ControllerComponents, ec: ExecutionContext): BodyParser[T] =
    controllerComponents.parsers.tolerantJson.map { json =>
      json.validate[T] match {
        case JsSuccess(value, _) => value
        case jsError: JsError => throw new Exception(jsError.toString)
      }
    }

}
