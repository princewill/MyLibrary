package model.dao

import javax.inject.Inject
import model.{BookId, BookInfo, BookTitle, Books}
import model.table.{BookTable, DbBook}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

trait BookDAO {

  def findByTitle(id: BookTitle): Future[Option[BookInfo]]
  def save(bookInfo: BookInfo): Future[BookId]
  def findAll: Future[Books]
  /*def delete(id: BookTitle): Unit
  def update(bookInfo: BookInfo): Future[BookInfo]*/

}

class BookDAOImpl @Inject()(@NamedDatabase("books") protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] with BookDAO {
  import BookDAOImpl._

  def save(bookInfo: BookInfo): Future[BookId] =
    findByTitle(bookInfo.book.title).flatMap {
      case Some(_) => throw new Exception("already exists!")
      case _ => db.run(BookTable.insertOrUpdate(DbBook._apply(bookInfo))).map(_ => bookInfo.id)
    }

  def findByTitle(title: BookTitle): Future[Option[BookInfo]] = db.run(BookTable.filter(_.title === title).result.headOption.map(_.map(BookInfo(_))))

  def findAll: Future[Books] = db.run(BookTable.result.map(_.map(BookInfo(_))))


  /*def delete
  def update
  def get*/

}

object BookDAOImpl {

  val BookTable = TableQuery[BookTable]

  lazy val CreateTable = DBIO.seq(BookTable.schema.createIfNotExists)

}

