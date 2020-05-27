package controllers

import java.util.UUID

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}
import model.Book
import model.services.BookService
import model.utils.ErrorException
import play.api.http.MimeTypes


@Singleton
class MyBook @Inject()(
  bookService: BookService
)(implicit val ec: ExecutionContext,
  val controllerComponents: ControllerComponents
) extends BaseController with ControllerHelper {


  def addBook(): Action[BookInfo] = Action.async(jsonBodyParser[BookInfo]) { implicit request =>
    val bookInfo = request.body
    val book = Book(UUID.randomUUID().toString, bookInfo)

    bookService
      .addBook(book)
      .map(bookId => Ok(bookId).as(MimeTypes.JSON)).recoverWith {
      case ex: ErrorException => Future.successful(BadRequest(ex.toJson))
    }
  }

  def getBooks: Action[AnyContent] = Action.async { implicit request =>
    bookService
      .getBooks
      .map(books => Ok(Json.toJson(books)))
  }

  def getBook(bookId: String): Action[AnyContent] = Action.async { implicit request =>
    bookService
      .getBook(bookId)
      .map(book => Ok(Json.toJson(book))).recoverWith {
      case ex: ErrorException => Future.successful(NotFound(ex.toJson))
    }
  }

  def deleteBook(bookId: String): Action[AnyContent] = Action.async { implicit request =>
    bookService
      .deleteBook(bookId)
      .map(successMsg => Ok(successMsg).as(MimeTypes.JSON)).recoverWith {
      case ex: ErrorException => Future.successful(NotFound(ex.toJson))
    }
  }

  //def deleteAll

}
