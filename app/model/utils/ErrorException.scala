package model.utils

import javax.inject.Singleton
import play.api.libs.json.{JsError, JsObject, Json}
import play.api.http.Status.{INTERNAL_SERVER_ERROR, BAD_REQUEST}


@Singleton
class ErrorException(
    status: Int,
    message: String,
    errors: collection.Seq[String] = Nil,
    header: Seq[(String, String)] = Nil
) extends Throwable {

  def toJson: JsObject =
    Json.obj(
      fields = "fail" -> message,
      "errorCode" -> status.toString,
      "errors" -> errors.map { error =>
        Json.obj(fields ="error" -> error)
      }
    )
}


object ErrorException {

  def apply(errorCodeStatus: Int, jsError: JsError): ErrorException = {
    if (errorCodeStatus >= 500) new ErrorException(INTERNAL_SERVER_ERROR, "Server Error!",  extractJsErrorMessage(jsError))
    new ErrorException(BAD_REQUEST, "Invalid JSON POST Data")
  }

  def apply(status: Int, message: String) = new ErrorException(status, message)

  private def extractJsErrorMessage(jsErrors: JsError): collection.Seq[String] = jsErrors.errors.map {
    case (path, validationErrors) => path.path.foldLeft("")((acc, p) => acc + p.toJsonString).drop(1) + " : " + validationErrors.map(_.message).mkString("")
  }

  def fromThrowable(status: Int): PartialFunction[Throwable, ErrorException] = {
    case ex: Throwable => new ErrorException(status, s"${ex.getClass.getName}:${ex.getMessage}")
  }

}
