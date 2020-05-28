package model.utils

import javax.inject.Singleton
import play.api.libs.json.{JsError, JsObject, Json}
import play.api.mvc.Results._
import play.api.mvc._


@Singleton
class ErrorException(
    status: Results#Status,
    message: String,
    errors: collection.Seq[String] = Nil,
    header: Seq[(String, String)] = Nil
) extends Throwable {

  def toJson: JsObject =
    Json.obj(
      fields = "fail" -> message,
      "errorCode" -> status.header.status,
      "errors" -> errors.map { error =>
        Json.obj(fields ="error" -> error)
      }
    )
}


object ErrorException {

  def apply(errorCodeStatus: Int, jsError: JsError): ErrorException = {
    if (errorCodeStatus >= 500) new ErrorException(InternalServerError, "Server Error!",  extractJsErrorMessage(jsError))
    new ErrorException(BadRequest, "Invalid JSON POST Data")
  }

  def apply(status: Results#Status, message: String) = new ErrorException(status, message)

  private def extractJsErrorMessage(jsErrors: JsError): collection.Seq[String] = jsErrors.errors.map {
    case (path, validationErrors) => path.path.foldLeft("")((acc, p) => acc + p.toJsonString).drop(1) + ": " + validationErrors.map(_.message).mkString("")
  }

  def fromThrowable(status: Option[Status]): PartialFunction[Throwable, ErrorException] = {
    case ex: Throwable => new ErrorException(status.getOrElse(InternalServerError), s"${ex.getClass.getName}:${ex.getMessage}")
  }

}
