package model

import play.api.libs.json.Reads

final case class Book(name: String, author: Option[String], genre: Option[String], isAudioBook: Option[Boolean])

final case class Books(books: Seq[Books])

object Book {


  implicit val reads: Reads[Book] = ???

}
