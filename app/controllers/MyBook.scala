package controllers

import java.util.UUID

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, BaseController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}
import model.BookInfo
import model.services.BookService

import scala.util.{Failure, Success, Try}


@Singleton
class MyBook @Inject()(
  bookService: BookService
)(implicit val ec: ExecutionContext,
  val controllerComponents: ControllerComponents
) extends BaseController with ControllerHelper {


  def addBook(): Action[Book] = Action.async(jsonBodyParser[Book]) { implicit request =>
    val book = request.body
    val bookInfo = BookInfo(UUID.randomUUID().toString, book)

    Try { bookService.addBooks(bookInfo) } match {
      case Success(value) => value.map{ bookId => Created(Json.toJson(bookId)) }
      case Failure(_) => Future.failed(new Exception("Request failed!"))
    }
  }



  /*def addBooks
  def getBooks
  def getBook
  def deleteBook
  def deleteAll
  def updateBook*/

}
