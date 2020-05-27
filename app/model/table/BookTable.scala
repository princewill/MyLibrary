package model.table

import model.{BookId, Book, BookTitle}
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.H2Profile.api._

case class DbBook(bookId: BookId, title: BookTitle, author: String, genre: String, isAudioBook: Boolean, isFiction: Boolean)

object DbBook{
  def _apply(book: Book): DbBook =
    DbBook(book.id, book.bookInfo.title, book.bookInfo.author, book.bookInfo.genre.genreString, book.bookInfo.isAudioBook, book.bookInfo.genre.isFiction)
}


class BookTable(tag: Tag) extends Table[DbBook](tag, _tableName = "books") {
  def bookId: Rep[BookId] = column[String]("book_id", O.PrimaryKey)

  def title: Rep[BookTitle] = column[String]("title")

  def author: Rep[String] = column[String]("author")
  def genre: Rep[String] = column[String]("genre")
  def isAudioBook: Rep[Boolean] = column[Boolean]("is_audio_book")
  def isFiction: Rep[Boolean] = column[Boolean]("is_fiction")

  override def * : ProvenShape[DbBook] = (bookId, title, author, genre, isAudioBook, isFiction) <> ((DbBook.apply _).tupled, DbBook.unapply)

}
