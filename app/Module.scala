import model.services.{BookService, BookServiceImpl}
import play.api.{Configuration, Environment}
import play.api.inject.Binding


class Module extends play.api.inject.Module {

  //TODO: "application has started..." log

  def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = Seq(
    //bind[BookDAO].to[BookDAOImpl],
    bind[BookService].to[BookServiceImpl]
  )
}

