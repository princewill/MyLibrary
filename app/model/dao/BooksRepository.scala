package model.dao

import controllers.Book
import javax.inject.Inject
import model.Genre.Romance
import model.{BookId, BookInfo, BookTitle}

import scala.concurrent.{ExecutionContext, Future}

abstract class BooksRepository {

  def findByTitle(title: BookTitle): Future[Option[BookInfo]]
  def save(bookInfo: BookInfo): Future[BookId]
}

class StaticBooksRepositoryImpl @Inject()(implicit val ec: ExecutionContext) extends BooksRepository {

  private var repo = scala.collection.mutable.Map("lordnap" -> BookInfo("c1fab850-648e-11e6-8b77-86f30ca893d3", Book("Lord Nap", "Jane smith", Romance, isAudioBook = false)))

  override def findByTitle(title: BookTitle): Future[Option[BookInfo]] = Future.successful(repo.get(title))

  override def save(bookInfo: BookInfo): Future[BookId] = findByTitle(bookInfo.book.title).map{
    case Some(_) => throw new Exception("Books with that title already exists!")
    case _ => repo += (bookInfo.book.title -> bookInfo); bookInfo.id

  }

}
