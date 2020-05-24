package model

import java.util.UUID

import controllers.Book
import play.api.libs.json._

final case class BookInfo(id: BookId, book: Book)

object BookInfo {

 implicit val writes: Writes[BookInfo] = Json.writes[BookInfo]

}
