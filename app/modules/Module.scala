package modules

import model.dao.{BookDAO, BookDAOImpl}
import model.services.{BookService, BookServiceImpl}
import model.utils.Logger
import play.api.inject.Binding
import play.api.{Configuration, Environment}


class Module extends play.api.inject.Module with Logger {

  logger.info(message = "Application has started...")

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = Seq(
    bind[DbInit].toSelf.eagerly(),
    bind[BookDAO].to[BookDAOImpl],
    //bind[BooksRepository].to[StaticBooksRepositoryImpl]
    bind[BookService].to[BookServiceImpl]
  )

}

