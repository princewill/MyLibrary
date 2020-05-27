package model.dao

import javax.inject.Inject
import model.{Book, BookId, BookTitle}
import model.table.{BookTable, DbBook}
import model.utils.ErrorException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import play.api.mvc.Results.BadRequest
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext
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
)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] with BookDAO {
  import BookDAOImpl._

  def save(book: Book): Future[BookId] =
    checkForExists(book.bookInfo.title) { _ =>
      db.run(BookTable.insertOrUpdate(DbBook._apply(book))).map(_ => book.id)
    }.recoverWith {
      case ex => Future.failed(ErrorException.fromThrowable(None)(ex))
    }

  def findById(bookId: BookId): Future[Option[DbBook]] = db.run(ById(bookId).result.headOption)

  def getAll: Future[Seq[DbBook]] = db.run(BookTable.result)

  def delete(bookId: BookId): Future[String] =
    checkForNonExists(bookId){ _ =>
      db.run(ById(bookId).delete).map(_ => "Book has been deleted successfully!")
    }.recoverWith {
      case ex => Future.failed(ErrorException.fromThrowable(None)(ex))
    }

  private def checkForExists(bookTitle: BookTitle)(fe: BookId => Future[String]): Future[String] = {
    findByTitle(bookTitle).flatMap {
      case Some(_) => Future.failed(ErrorException(BadRequest, "Book Already Exists!"))
      case _ => fe(bookTitle)
    }
  }

  private def checkForNonExists(bookId: BookId)(fe: BookId => Future[String]): Future[String] = {
    findById(bookId).flatMap {
      case None => Future.failed(ErrorException(BadRequest, "Book Does Not Exists!"))
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

  /*lazy val BookReturningRow =
    BookTable returning BookTable.map(_.bookId) into { (book, id) => book.copy(bookId = id)}*/

}

