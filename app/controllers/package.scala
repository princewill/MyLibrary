import play.api.libs.json.Reads
import play.api.libs.functional.syntax._
import play.api.libs.json._
import model.Genre.Autobiography
import model.{BookId, BookTitle, DbBook, Genre}
import model.utils.JsonReadsHelpers


package object controllers {
  type Books = Seq[Book]

  final case class BookInfo(title: BookTitle, author: String, genre: Genre, isAudioBook: Boolean)

  object BookInfo {

    //TODO: create helper to remove white space from title and lowercase

    implicit val reads: Reads[BookInfo] = (
      (__ \ "bookTitle").read[BookTitle](JsonReadsHelpers.NonEmptyString).map(str => str.toLowerCase.replaceAll(" ", "")) and
        (__ \ "bookAuthor").read[String](JsonReadsHelpers.NonEmptyString) and
        (__ \ "bookGenre").readWithDefault[Genre](Autobiography) and
        (__ \ "isAudioBook").readWithDefault[Boolean](defaultValue = false)
      )(BookInfo.apply _)

    implicit val writes: Writes[BookInfo] = Json.writes[BookInfo]
  }

  final case class Book(id: BookId, bookInfo: BookInfo)

  object Book {

    def fromDbBook(dbBook: DbBook): Book = Book(dbBook.bookId, BookInfo(dbBook.title, dbBook.author, Genre(dbBook.genre), dbBook.isAudioBook))

    implicit val reads: Reads[Book] = (
      (__ \ "id").read[BookId](JsonReadsHelpers.NonEmptyString) and
        (__ \ "bookDetails").read[BookInfo]
      )(Book.apply _)

    implicit val writes: Writes[Book] = Json.writes[Book]

  }

}
