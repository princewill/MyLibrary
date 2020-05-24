package model

import model.utils.JsonReadsHelpers
import play.api.libs.json._

sealed trait Genre {
  val genre: String
  val isFiction: Boolean
}

object Genre {

  case object Romance extends Genre { val genre = "romance"; val isFiction = true }

  case object Crime extends Genre { val genre = "crime"; val isFiction = true }

  case object Drama extends Genre { val genre = "drama"; val isFiction = true }

  case object Comic extends Genre { val genre = "comic"; val isFiction = true }

  case object Fantasy extends Genre { val genre = "fantasy"; val isFiction = true }

  case object SciFi extends Genre { val genre = "scifi"; val isFiction = true }

  case object Thriller extends Genre { val genre = "thriller"; val isFiction = true }

  case object Horror extends Genre { val genre = "horror"; val isFiction = true }

  case object TODO extends Genre { val genre = "todo"; val isFiction = true }

  case object Religion extends Genre { val genre = "religion"; val isFiction = false }

  case object Science extends Genre { val genre = "science"; val isFiction = false }

  case object History extends Genre { val genre = "history"; val isFiction = false }

  case object Autobiography extends Genre { val genre = "autobiography"; val isFiction = false }

  case object TextBook extends Genre { val genre = "textbook"; val isFiction = false }


  implicit val reads: Reads[Genre] = __.read[String](JsonReadsHelpers.NonEmptyString).map(str => Genre(str.toLowerCase))

  implicit val writes: Writes[Genre] = (value: Genre) => JsString(value.genre)

  def apply(str: String): Genre = str match {
    case Romance.genre => Romance
    case Crime.genre => Crime
    case Drama.genre => Drama
    case Comic.genre => Comic
    case Fantasy.genre => Fantasy
    case SciFi.genre => SciFi
    case Thriller.genre => Thriller
    case Horror.genre => Horror
    case Religion.genre => Religion
    case Science.genre => Science
    case History.genre => History
    case Autobiography.genre => Autobiography
    case TextBook.genre => TextBook
    case _  => TODO
  }

}


