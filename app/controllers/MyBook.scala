package controllers

import javax.inject.Inject
import model.Book
import model.services.BookService
import play.api.mvc.{Action, BaseController, ControllerComponents, ControllerHelpers}

import scala.concurrent.Future


@Singleton
class MyBook @Inject()(implicit val controllerComponents: ControllerComponents, bookService: BookService) extends BaseController with ControllerHelpers {

  def addBook(): Action[Book] = Action.async(jsonBodyParser[Book]) { implicit request =>
    val book = request.body


  }



  def addBooks
  def getBooks
  def getBook
  def deleteBook
  def deleteAll
  def updateBook

}
