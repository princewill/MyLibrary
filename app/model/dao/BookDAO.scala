package model.dao

import model.{BookId, BookInfo}

import scala.concurrent.Future

trait BookDAO {

  def save(bookInfo: BookInfo): Future[BookInfo]
  def delete(id: BookId): Unit
  def update(bookInfo: BookInfo): Future[BookInfo]
  def find(id: BookId): Future[Option[BookInfo]]

}


