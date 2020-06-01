package utils

import model.utils.ErrorException
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.json.{JsError, JsObject, JsSuccess, Json, Reads}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest

object TestHelper extends TestHelperRequestsAndResponse {

    implicit class FakeRequestHelper(private val fakeRequest: FakeRequest[AnyContentAsEmpty.type]) extends AnyVal {
      def withT[T](json: String)(implicit reads: Reads[T]): FakeRequest[T] = {
        val jsonObj = Json.parse(json)
        val body = jsonObj.validate[T] match {
          case jsError: JsError => throw ErrorException(400, jsError)
          case value            => value.get
        }
        fakeRequest.withBody(body).withHeaders(HeaderNames.CONTENT_TYPE -> MimeTypes.JSON)
      }
    }
}
