import model.Genre
import play.api.libs.json.Reads
import model.Genre.Autobiography
import model.utils.JsonReadsHelpers
import play.api.libs.functional.syntax._
import play.api.libs.json._


package object controllers {

  final case class Book(name: String, author: String, genre: Genre, isAudioBook: Boolean)

  final case class Books(books: Seq[Books])

  object Book {

    implicit val reads: Reads[Book] = (
      (__ \ "bookName").read[String](JsonReadsHelpers.NonEmptyString) and
        (__ \ "bookAuthor").read[String](JsonReadsHelpers.NonEmptyString) and
        (__ \ "bookGenre").readWithDefault[Genre](Autobiography) and
        (__ \ "isAudioBook").readWithDefault[Boolean](defaultValue = false)
      )(Book.apply _)

    implicit val writes: Writes[Book] = Json.writes[Book]

  }

}
