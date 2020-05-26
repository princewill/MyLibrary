import play.api.libs.json.Reads
import play.api.libs.functional.syntax._
import play.api.libs.json._

import model.Genre.Autobiography
import model.{BookTitle, Genre}
import model.utils.JsonReadsHelpers


package object controllers {

  final case class Book(title: BookTitle, author: String, genre: Genre, isAudioBook: Boolean)

  object Book {

    //TODO: create helper to remove white space from title and lowercase

    implicit val reads: Reads[Book] = (
      (__ \ "bookTitle").read[BookTitle](JsonReadsHelpers.NonEmptyString).map(str => str.toLowerCase.replaceAll(" ", "")) and
        (__ \ "bookAuthor").read[String](JsonReadsHelpers.NonEmptyString) and
        (__ \ "bookGenre").readWithDefault[Genre](Autobiography) and
        (__ \ "isAudioBook").readWithDefault[Boolean](defaultValue = false)
      )(Book.apply _)

    implicit val writes: Writes[Book] = Json.writes[Book]
  }

}
