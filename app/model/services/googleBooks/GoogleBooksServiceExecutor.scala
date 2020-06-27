package model.services.googleBooks

import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.Uri.Query
import javax.inject.Inject
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.{WSClient, WSResponse}
import model.utils.RestJsonException

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class GoogleBooksServiceExecutor @Inject()(wsClient: WSClient) {

  private def parseWSResponseBody(host: String, response: WSResponse): JsValue = {
    try{Json.parse(response.body)
    } catch {
      case NonFatal(ex) => throw RestJsonException(ex, host, response)
    }
  }

  def execute[T](url: Uri, queryParams: Query)(func: JsValue => T)(implicit ec: ExecutionContext): Future[T] = {
    val completeUrl = url.withQuery(queryParams)
    wsClient.url(completeUrl.toString()).get().map{ wsr =>
      val responseBody = parseWSResponseBody(url.scheme, wsr)
      func(responseBody)
    }
  }

}
