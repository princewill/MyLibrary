package utils

import play.api.libs.json.{JsArray, JsObject, Json}

trait TestHelperRequestsAndResponse {
  val addBookRequest: JsObject =
    Json.obj(
      "bookTitle" -> "Lord of the Rings",
      "bookAuthor" -> "J. R. R. Tolkien",
      "bookGenre" -> "fantasy",
      "isAudioBook"-> false
    )

  val addBookErrorResponse: JsObject =
    Json.obj(
      "fail" -> "Book Already Exists!",
      "errorCode" -> "400",
      "errors" -> JsArray.empty
    )

  val getBookErrorResponse: JsObject =
    Json.obj(
      "fail" -> "Book Does Not Exist!",
      "errorCode" -> "404",
      "errors" -> JsArray.empty
    )

  val getBookResponse: JsObject = Json.obj(
    "title" -> "lordoftherings",
    "author" -> "J. R. R. Tolkien",
    "genre" -> "fantasy",
    "isAudioBook" -> false
  )
}
