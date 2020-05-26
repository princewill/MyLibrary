package model

import model.utils.JsonReadsHelpers
import play.api.libs.json._

sealed trait Genre {
  val genreString: String
  val isFiction: Boolean
}

object Genre {

  case object Romance extends Genre { val genreString = "romance"; val isFiction = true }

  case object Crime extends Genre { val genreString = "crime"; val isFiction = true }

  case object Drama extends Genre { val genreString = "drama"; val isFiction = true }

  case object Comic extends Genre { val genreString = "comic"; val isFiction = true }

  case object Fantasy extends Genre { val genreString = "fantasy"; val isFiction = true }

  case object SciFi extends Genre { val genreString = "scifi"; val isFiction = true }

  case object Thriller extends Genre { val genreString = "thriller"; val isFiction = true }

  case object Horror extends Genre { val genreString = "horror"; val isFiction = true }

  case object TODO extends Genre { val genreString = "todo"; val isFiction = true }

  case object Religion extends Genre { val genreString = "religion"; val isFiction = false }

  case object Science extends Genre { val genreString = "science"; val isFiction = false }

  case object History extends Genre { val genreString = "history"; val isFiction = false }

  case object Autobiography extends Genre { val genreString = "autobiography"; val isFiction = false }

  case object TextBook extends Genre { val genreString = "textbook"; val isFiction = false }


  implicit val reads: Reads[Genre] = __.read[String](JsonReadsHelpers.NonEmptyString).map(str => Genre(str.toLowerCase))

  implicit val writes: Writes[Genre] = (value: Genre) => JsString(value.genreString)

  def apply(str: String): Genre = str match {
    case Romance.genreString => Romance
    case Crime.genreString => Crime
    case Drama.genreString => Drama
    case Comic.genreString => Comic
    case Fantasy.genreString => Fantasy
    case SciFi.genreString => SciFi
    case Thriller.genreString => Thriller
    case Horror.genreString => Horror
    case Religion.genreString => Religion
    case Science.genreString => Science
    case History.genreString => History
    case Autobiography.genreString => Autobiography
    case TextBook.genreString => TextBook
    case _  => TODO
  }

}


