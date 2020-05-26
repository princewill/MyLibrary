package model.services

import javax.inject.Inject
import model.{BookId, BookInfo}
import model.dao.BookDAO

import scala.concurrent.{ExecutionContext, Future}


trait BookService {

  def addBooks(bookInfo: BookInfo): Future[BookId]


  /*def getBooks(ids: List[BookTitle]): Future[Books]
  def getBook(id: BookTitle): Future[Book]
  def deleteBook(id: BookTitle): Future[Ack]
  def deleteAll(ids: List[BookTitle]): Future[Ack]
  def updateBook(book: Book): Future[Book]*/

}

class BookServiceImpl @Inject()(bookDAO: BookDAO)(implicit ec: ExecutionContext) extends BookService {

  def addBooks(bookInfo: BookInfo): Future[BookId] = bookDAO.save(bookInfo)



  /*def getBooks(ids: List[BookTitle]): Future[Books]
  def getBook(id: BookTitle): Future[Book]
  def deleteBook(id: BookTitle): Future[Ack]
  def deleteAll(ids: List[BookTitle]): Future[Ack]
  def updateBook(book: Book): Future[Book]
*/

}
