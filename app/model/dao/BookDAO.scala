package model.dao

import controllers.Book
import javax.inject.Inject
import model.{BookId, BookTitle, DbBook}
import model.table.BookTable
import model.utils.DatabaseExecutionContext
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

trait BookDAO {

  def findById(id: BookId): Future[Option[DbBook]]

  def findByTitle(bookTitle: BookTitle): Future[Option[DbBook]]

  def insertOrUpdate(book: Book): Future[BookId]

  def findAll: Future[Seq[DbBook]]

  def deleteById(id: BookId): Future[Int]

}

class BookDAOImpl @Inject()(
 @NamedDatabase("books") protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: DatabaseExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] with BookDAO {
  import BookDAOImpl._

  def insertOrUpdate(book: Book): Future[BookId] = db.run(BookTable.insertOrUpdate(DbBook.fromBook(book))).map(_ => book.id)

  def findById(bookId: BookId): Future[Option[DbBook]] = db.run(ById(bookId).result.headOption)

  def findByTitle(bookTitle: BookTitle): Future[Option[DbBook]] = db.run(BookTable.filter(_.title === bookTitle).result.headOption)

  def findAll: Future[Seq[DbBook]] = db.run(BookTable.result)

  def deleteById(bookId: BookId): Future[Int] = db.run(ById(bookId).delete)


}

object BookDAOImpl {

  val BookTable = TableQuery[BookTable]

  lazy val CreateTable = DBIO.seq(BookTable.schema.createIfNotExists)

  lazy val ById: BookId => Query[BookTable, DbBook, Seq] = (id: BookId) => BookTable.filter(_.bookId === id)

}

