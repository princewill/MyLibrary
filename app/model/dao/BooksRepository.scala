package model.dao

import controllers.BookInfo
import javax.inject.Inject
import model.Genre.Romance
import model.{Book, BookId, BookTitle}

import scala.concurrent.{ExecutionContext, Future}

abstract class BooksRepository {

  def findById(bookId: BookId): Future[Option[Book]]
  def save(book: Book): Future[BookId]
}

class StaticBooksRepositoryImpl @Inject()(implicit val ec: ExecutionContext) extends BooksRepository {

  private var repo = scala.collection.mutable.Map("c1fab850-648e-11e6-8b77-86f30ca893d3" -> Book("c1fab850-648e-11e6-8b77-86f30ca893d3", BookInfo("Lord Nap", "Jane smith", Romance, isAudioBook = false)))

  override def findById(bookId: BookId): Future[Option[Book]] = Future.successful(repo.get(bookId))

  override def save(book: Book): Future[BookId] = findById(book.bookInfo.title).map{
    case Some(_) => throw new Exception("Books with that title already exists!")
    case _ => repo += (book.id -> book); book.id

  }

}
