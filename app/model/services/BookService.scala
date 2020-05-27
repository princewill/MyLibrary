package model.services

import javax.inject.Inject
import model.{Book, BookId, Books}
import model.dao.BookDAO
import model.utils.{DatabaseExecutionContext, ErrorException}

import scala.concurrent.{ExecutionContext, Future}


trait BookService {

  def addBook(book: Book): Future[BookId]

  def getBooks: Future[Books]

  def getBook(id: BookId): Future[Book]

  def deleteBook(id: BookId): Future[String]

  //def updateBook(book: Book): Future[Book]

  //def deleteAll(ids: List[BookTitle]): Future[Ack]


}

class BookServiceImpl @Inject()(bookDAO: BookDAO)(implicit ec: DatabaseExecutionContext) extends BookService {

  def addBook(book: Book): Future[BookId] = bookDAO.save(book)

  def getBooks: Future[Books] =
    bookDAO.getAll.map(_.map(Book(_)))

  def getBook(id: BookId): Future[Book] =
    bookDAO.findById(id).map {
      case Some(dbBook) => Book(dbBook)
      case _ => throw ErrorException(play.api.mvc.Results.BadRequest, "Book Does Not Exist!")
    }

  def deleteBook(id: BookId): Future[String] = bookDAO.delete(id)

  /*
  def deleteAll(ids: List[BookTitle]): Future[Ack]
  def updateBook(book: Book): Future[Book]
*/

}
