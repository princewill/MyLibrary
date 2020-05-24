package model.utils

import play.api.libs.json.{JsError, JsString, JsSuccess, JsValue, Json, JsonValidationError, Reads}

object JsonReadsHelpers {

  val NonEmptyString: Reads[String] =  Reads.StringReads.filterNot(JsonValidationError("String can not be empty!"))(_.isEmpty)

  //TODO: fix xxs-safe check
  /*val XSSSafeString: Reads[String] = {
    case JsString(value) if !StringHelpers.hasUnsafeHTMLTags(value) => JsSuccess(value)
    case jsValue: JsValue => JsError(s"${Json.prettyPrint(jsValue)} is not XSS-SAFE")
  }*/

}
