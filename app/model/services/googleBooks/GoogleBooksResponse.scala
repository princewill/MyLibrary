package model.services.googleBooks

import play.api.libs.json.{JsNull, JsValue}

import scala.collection.immutable.Iterable

case class GoogleBooksResponse(title: String, author: String, ratings: String)


object GoogleBooksResponse {

  def apply(json: JsValue): GoogleBooksResponse = {
    val item = (json \ "items").asOpt[Iterable[JsValue]].getOrElse(Nil).headOption.getOrElse(JsNull)
    val volumeInfo = (item \ "volumeInfo").as[JsValue]
    val title = (volumeInfo \ "title").as[String]
    val author = (volumeInfo \ "authors").as[Iterable[String]].headOption.getOrElse("")
    //val ratings = (volumeInfo \ "averageRating").as[String].toString
    GoogleBooksResponse(title, author, "ratings")
  }
}