package model.services

import javax.inject.Inject
import model.BookInfo
import model.dao.BookDAO
import scala.concurrent.ExecutionContext.Implicits._

import scala.concurrent.Future

class BookServiceImpl @Inject() (bookDao: BookDAO) extends BookService {

  def addBooks(bookInfo: BookInfo): Future[BookInfo] = {
    bookDao.find(bookInfo.id).flatMap{
      case Some(_) => throw new Exception
      case None => bookDao.save(bookInfo)
    }
  }





  /*def getBooks(ids: List[BookId]): Future[Books]
  def getBook(id: BookId): Future[Book]
  def deleteBook(id: BookId): Future[Ack]
  def deleteAll(ids: List[BookId]): Future[Ack]
  def updateBook(book: Book): Future[Book]
*/

}
