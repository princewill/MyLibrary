package model.utils

import javax.inject.Inject
import model.dao.BookDAOImpl
import play.api.db.slick.DatabaseConfigProvider
import play.db.NamedDatabase
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


class InitData @Inject()(@NamedDatabase("books") protected val dbConfigProvider: DatabaseConfigProvider, implicit val ec: ExecutionContext) {

  def createDBInitData(): Future[Unit] = dbConfigProvider.get[JdbcProfile].db.run(BookDAOImpl.CreateTable)

}
