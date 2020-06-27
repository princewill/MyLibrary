package controllers

import java.util.UUID

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Result}

import scala.concurrent.{ExecutionContext, Future}
import model.services.BookService
import model.services.googleBooks.GoogleBooksService
import model.utils.ErrorException
import play.api.http.MimeTypes


@Singleton
class MyBook @Inject()(
  bookService: BookService,
  googleBooksService: GoogleBooksService
)(implicit val ec: ExecutionContext,
  val controllerComponents: ControllerComponents
) extends BaseController with ControllerHelper {

  implicit val mb: MyBook = this
  import MyBook._

  def addBook(): Action[BookInfo] = Action.async(jsonBodyParser[BookInfo]) { implicit request =>
    val bookInfo = request.body

    val rr =
      for {
        response <- googleBooksService.getVolume(bookInfo.title)
        resp <- {
          val _bookInfo = bookInfo.copy(title = response.title, author = response.author)
          bookService.addBook(Book(UUID.randomUUID().toString, _bookInfo))
        }} yield resp

      rr.map(resultWrapper).recoverWith {
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
      .map(resultWrapper).recoverWith {
      case ex: ErrorException => Future.successful(NotFound(ex.toJson))
    }
  }

  def deleteBook(bookId: String): Action[AnyContent] = Action.async { implicit request =>
    bookService
      .deleteBook(bookId)
      .map(resultWrapper).recoverWith {
      case ex: ErrorException => Future.successful(NotFound(ex.toJson))
    }
  }

  def updateBook(): Action[Book] = Action.async(jsonBodyParser[Book]) { implicit request =>
    val book = request.body
    bookService
      .updateBook(book)
      .map(resultWrapper).recoverWith {
      case ex: ErrorException => Future.successful(NotFound(ex.toJson))
    }
  }

}

object MyBook {
  private def resultWrapper[T](implicit myBook: MyBook, writes: play.api.libs.json.Writes[T]): T => Result =
    (value: T) => myBook.Ok(Json.toJson(value)).as(MimeTypes.JSON)

  /*private def errorResultWrapper[T](implicit myBook: MyBook): ErrorException => Future[Result] = (ex: ErrorException) =>
    Future.successful(myBook.NotFound(ex.toJson))*/

}
