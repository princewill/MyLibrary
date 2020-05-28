package controllers

import model.services.BookService
import model.utils.ErrorException
import org.specs2.specification.Scope
import play.api.libs.json._
import play.api.mvc.{AnyContentAsEmpty, Result}
import play.api.test.FakeRequest
import utils.BaseSpec
import utils.TestHelper._

import scala.concurrent.{ExecutionContext, Future}


class MyBookSpec extends BaseSpec {

  trait MyBookFixture extends Scope {

    val myBookController: MyBook = app.injector.instanceOf[MyBook]
    implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global
    val req: FakeRequest[AnyContentAsEmpty.type] = FakeRequest()

  }

  "addBook" should {
    import utils.TestHelper.FakeRequestHelper

    "return 200 OK when book does not already exist in DB" in new MyBookFixture {

      val postReq: FakeRequest[BookInfo] = req.withT(Json.stringify(addBookRequest))
      val result: Future[Result] = myBookController.addBook()(postReq)

      status(result) must beEqualTo(OK)
    }

    "return 400 BadRequest when book already exist in DB" in new MyBookFixture {

      val postReq: FakeRequest[BookInfo] = req.withT(Json.stringify(addBookRequest))
      val result: Future[Result] = myBookController.addBook()(postReq)

      contentAsJson(result) must beEqualTo(addBookErrorResponse)
      status(result) must beEqualTo(BAD_REQUEST)
    }

    "throw an ErrorException when bad post request" in new MyBookFixture {

      req.withT(Json.stringify(Json.obj())) must throwA[ErrorException].like {
        case error: ErrorException => (error.toJson \ "fail").as[String] must beEqualTo("Invalid JSON POST Data")
      }

    }
  }

  "getBooks" should {

    "return 200 OK with list of books" in new MyBookFixture {

      val result: Future[Result] = myBookController.getBooks()(req)
      val resultJson: Seq[JsValue] = contentAsJson(result).as[List[JsValue]]

      resultJson.head \ "bookInfo" must beEqualTo(JsDefined(getBookResponse))
      resultJson.size must beEqualTo(1)
      status(result) must beEqualTo(OK)
    }
  }

  "getBook" should {

    "return 200 OK when book exist in DB" in new MyBookFixture {

      val getBooksResult: JsValue = contentAsJson(myBookController.getBooks()(req)).as[List[JsValue]].head
      val id: String = (getBooksResult \ "id").as[String]
      val result: Future[Result] = myBookController.getBook(id)(req)

      (contentAsJson(result) \ "bookInfo").as[JsValue] must beEqualTo(getBookResponse)
      status(result) must beEqualTo(OK)
    }

    "return 404 Not Found when book does not exist in DB" in new MyBookFixture {

      val result: Future[Result] = myBookController.getBook(bookId = "")(req)

      contentAsJson(result) must beEqualTo(getBookErrorResponse)
      status(result) must beEqualTo(NOT_FOUND)
    }
  }

  "deleteBook" should {

    "return 404 Not Found when book does not exist in DB" in new MyBookFixture {

      val result: Future[Result] = myBookController.deleteBook(bookId = "")(req)

      contentAsJson(result) must beEqualTo(getBookErrorResponse)
      status(result) must beEqualTo(NOT_FOUND)
    }

    "return 200 OK when book exist in DB" in new MyBookFixture {

      val getBooksResult: JsValue = contentAsJson(myBookController.getBooks()(req)).as[List[JsValue]].head
      val id: String = (getBooksResult \ "id").as[String]
      val result: Future[Result] = myBookController.deleteBook(id)(req)

      contentAsString(result) must beEqualTo("Book has been deleted successfully!")
      status(result) must beEqualTo(OK)
    }

  }
}
