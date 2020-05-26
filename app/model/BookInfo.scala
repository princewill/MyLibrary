package model

import controllers.Book
import model.table.DbBook
import play.api.libs.json._

final case class BookInfo(id: BookId, book: Book)

object BookInfo {

  def apply(dbBook: DbBook): BookInfo = BookInfo(dbBook.bookId, Book(dbBook.title, dbBook.author, Genre(dbBook.genre), dbBook.isAudioBook))

 implicit val writes: Writes[BookInfo] = Json.writes[BookInfo]

}
