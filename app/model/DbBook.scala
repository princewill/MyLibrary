package model

import controllers.Book

case class DbBook(bookId: BookId, title: BookTitle, author: String, genre: String, isAudioBook: Boolean, isFiction: Boolean)

object DbBook{
  def fromBook(book: Book): DbBook =
    DbBook(book.id, book.bookInfo.title, book.bookInfo.author, book.bookInfo.genre.genreString, book.bookInfo.isAudioBook, book.bookInfo.genre.isFiction)
}

