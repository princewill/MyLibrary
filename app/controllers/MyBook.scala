package controllers

import java.util.UUID

import javax.inject.{Inject, Singleton}
import model.BookInfo
import model.services.BookService
import play.api.libs.json.Json
import play.api.mvc.{Action, BaseController, ControllerComponents}
import scala.concurrent.ExecutionContext.Implicits._


@Singleton
class MyBook @Inject()(implicit val controllerComponents: ControllerComponents, bookService: BookService) extends BaseController with ControllerHelper {

  def addBook(): Action[Book] = Action.async(jsonBodyParser[Book]) { implicit request =>
    val book = request.body
    val bookInfo = BookInfo(UUID.randomUUID().toString, book)

    bookService.addBooks(bookInfo).map(bookInfo => Ok(Json.toJson(bookInfo)))
  }



  /*def addBooks
  def getBooks
  def getBook
  def deleteBook
  def deleteAll
  def updateBook*/

}
