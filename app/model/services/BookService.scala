package model.services

import controllers.{Book, Books}
import javax.inject.Inject
import model.BookId
import model.dao.BookDAO
import model.utils.{DatabaseExecutionContext, ErrorException}
import play.api.http.Status.{BAD_REQUEST, INTERNAL_SERVER_ERROR, NOT_FOUND}

import scala.concurrent.Future

trait BookService {

  def addBook(book: Book): Future[BookId]

  def getBooks: Future[Books]

  def getBook(id: BookId): Future[Book]

  def deleteBook(id: BookId): Future[String]

  def updateBook(book: Book): Future[BookId]

  def deleteAll(): Future[String]

}

class BookServiceImpl @Inject()(bookDAO: BookDAO)(implicit ec: DatabaseExecutionContext) extends BookService {

  def addBook(book: Book): Future[BookId] =
    checkForExists(book) {
      bookDAO.insertOrUpdate(_).recoverWith {
        case ex => Future.failed(ErrorException.fromThrowable(INTERNAL_SERVER_ERROR)(ex))
      }
    }

  def getBooks: Future[Books] = bookDAO.findAll.map(_.map(Book.fromDbBook))

  def getBook(id: BookId): Future[Book] =
    bookDAO.findById(id).map {
        case Some(dbBook) => Book.fromDbBook(dbBook)
        case _ => throw ErrorException(NOT_FOUND, "Book Does Not Exist!")
    }

  def deleteBook(id: BookId): Future[String] =
    checkForNonExists(id) {
      bookDAO.deleteById(_).map(_ => "Book has been deleted successfully!").recoverWith {
        case ex => Future.failed(ErrorException.fromThrowable(INTERNAL_SERVER_ERROR)(ex))
      }
    }

  def updateBook(book: Book): Future[BookId] =
    checkForNonExists(book.id){ _ =>
      bookDAO.insertOrUpdate(book)
    }

  def deleteAll(): Future[String] =
    bookDAO.deleteAll().map(_ => "All Books have been Deleted!").recoverWith {
      case ex => Future.failed(ErrorException.fromThrowable(INTERNAL_SERVER_ERROR)(ex))
    }


  private def checkForExists(book: Book)(fe: Book => Future[String]): Future[String] = {
    bookDAO.findByTitle(book.bookInfo.title).flatMap {
      case Some(_) => Future.failed(ErrorException(BAD_REQUEST, "Book Already Exists!"))
      case _ => fe(book)
    }
  }

  private def checkForNonExists(bookId: BookId)(fe: BookId => Future[String]): Future[String] = {
    bookDAO.findById(bookId).flatMap {
      case None => Future.failed(ErrorException(NOT_FOUND, "Book Does Not Exist!"))
      case _ => fe(bookId)
    }
  }

}
