package model.table

import model.{BookId, DbBook, BookTitle}
import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.H2Profile.api._



class BookTable(tag: Tag) extends Table[DbBook](tag, _tableName = "books") {
  def bookId: Rep[BookId] = column[String]("book_id", O.PrimaryKey)

  def title: Rep[BookTitle] = column[String]("title")

  def author: Rep[String] = column[String]("author")
  def genre: Rep[String] = column[String]("genre")
  def isAudioBook: Rep[Boolean] = column[Boolean]("is_audio_book")
  def isFiction: Rep[Boolean] = column[Boolean]("is_fiction")

  override def * : ProvenShape[DbBook] = (bookId, title, author, genre, isAudioBook, isFiction) <> ((DbBook.apply _).tupled, DbBook.unapply)

}
