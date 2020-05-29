/*
package model.dao

import javax.inject.Inject
import model.utils.DatabaseExecutionContext
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.db.NamedDatabase
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait DatabaseManager[F[_], DB[_]] {
  def execute[A](action: DB[A]): F[A]
  def executeTransitionally[A](action: DB[A]): F[A]
}


class SlickDatabaseManager@Inject()(
 @NamedDatabase("books") protected val dbConfigProvider: DatabaseConfigProvider
)(implicit ec: DatabaseExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] with DatabaseManager[Future, DBIO] {

  override def execute[A](action: DBIO[A]): Future[A] =
    db.run(action)
  override def executeTransitionally[A](action:DBIO[A]): Future[A] =
    db.run(action.nonFusedEquivalentAction)

}*/
