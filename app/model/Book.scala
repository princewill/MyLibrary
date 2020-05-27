package model

import controllers.BookInfo
import model.table.DbBook
import play.api.libs.json._

final case class Book(id: BookId, bookInfo: BookInfo)

object Book {

  def apply(dbBook: DbBook): Book = Book(dbBook.bookId, BookInfo(dbBook.title, dbBook.author, Genre(dbBook.genre), dbBook.isAudioBook))

  implicit val writes: Writes[Book] = Json.writes[Book]

}
