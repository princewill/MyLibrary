package model.utils

import play.api.libs.ws.WSResponse

import scala.util.control.NoStackTrace

case class RestJsonException(
 cause: Throwable,
 requestHost: String,
 response: WSResponse
) extends RuntimeException(s"$requestHost ${response.status} Could not parse JSON from service response", cause) with NoStackTrace