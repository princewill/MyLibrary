package utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import controllers.BookInfo
import model.utils.ErrorException
import play.api.Configuration
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc.AnyContentAsEmpty
import play.api.test.FakeRequest

object TestHelper extends TestHelperRequestsAndResponse {
  val actorSystem: ActorSystem = ActorSystem.create("Specs2")
  val materializer: ActorMaterializer = ActorMaterializer()(actorSystem)
  val conf = new Configuration(ConfigFactory.load("application.conf"))

  object FakeRequestHelper {
    implicit class FakeRequestHelper(private val fakeRequest: FakeRequest[AnyContentAsEmpty.type]) extends AnyVal {
      def withT(json: String)(implicit reads: Reads[BookInfo]): FakeRequest[BookInfo] = {
        val jsonObj = Json.parse(json)
        val body = jsonObj.validate[BookInfo] match {
          case JsSuccess(value, _) => value
          case jsError: JsError       => throw ErrorException(400, jsError)
        }
        fakeRequest.withBody(body).withHeaders(HeaderNames.CONTENT_TYPE -> MimeTypes.JSON)
      }
    }
  }


}
