package model.dao

import javax.inject.Inject
import model.{Book, BookId, BookTitle}
import model.table.{BookTable, DbBook}
import model.utils.{DatabaseExecutionContext, ErrorException}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import play.api.http.Status.{INTERNAL_SERVER_ERROR, BAD_REQUEST, NOT_FOUND}
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

trait BookDAO {

  def findById(id: BookId): Future[Option[DbBook]]
  def save(book: Book): Future[BookId]
  def getAll: Future[Seq[DbBook]]
  def delete(id: BookId): Future[String]
  /*
  def update(bookInfo: BookInfo): Future[BookInfo]*/

}

class BookDAOImpl @Inject()(
 @NamedDatabase("books") protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: DatabaseExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] with BookDAO {
  import BookDAOImpl._

  def save(book: Book): Future[BookId] =
    checkForExists(book.bookInfo.title) { _ =>
      db.run(BookTable.insertOrUpdate(DbBook._apply(book))).map(_ => book.id).recoverWith {
        case ex => Future.failed(ErrorException.fromThrowable(INTERNAL_SERVER_ERROR)(ex))
      }
    }

  def findById(bookId: BookId): Future[Option[DbBook]] = db.run(ById(bookId).result.headOption)

  def getAll: Future[Seq[DbBook]] = db.run(BookTable.result)

  def delete(bookId: BookId): Future[String] =
    checkForNonExists(bookId){ _ =>
      db.run(ById(bookId).delete).map(_ => "Book has been deleted successfully!").recoverWith {
        case ex => Future.failed(ErrorException.fromThrowable(INTERNAL_SERVER_ERROR)(ex))
      }
    }

  private def checkForExists(bookTitle: BookTitle)(fe: BookId => Future[String]): Future[String] = {
    findByTitle(bookTitle).flatMap {
      case Some(_) => Future.failed(ErrorException(BAD_REQUEST, "Book Already Exists!"))
      case _ => fe(bookTitle)
    }
  }

  private def checkForNonExists(bookId: BookId)(fe: BookId => Future[String]): Future[String] = {
    findById(bookId).flatMap {
      case None => Future.failed(ErrorException(NOT_FOUND, "Book Does Not Exist!"))
      case _ => fe(bookId)
    }
  }

  private def findByTitle(bookTitle: BookTitle): Future[Option[DbBook]] =
    db.run(BookTable.filter(_.title === bookTitle).result.headOption)

  //def update
}

object BookDAOImpl {

  val BookTable = TableQuery[BookTable]

  lazy val CreateTable = DBIO.seq(BookTable.schema.createIfNotExists)

  lazy val ById: BookId => Query[BookTable, DbBook, Seq] = (id: BookId) => BookTable.filter(_.bookId === id)

}

