package model.utils

class SearchTerms private(val value: String) extends AnyVal {

  def removeDoubleQuotes(): SearchTerms = new SearchTerms(value.replace("\"", ""))

  def removeAtSymbol(value: String) = new SearchTerms(value.replace("@", " "))

  override def toString: String = value

}


object SearchTerms {

  def clean(value: String): SearchTerms = {
    val cleaned = value.map{
      case '<' => ' '
      case c if c.isControl => ' '
      case c if c.isWhitespace => ' '
      case c => c
    }.foldLeft("") {
      (s, c) => if (s.endsWith(" ") && c == ' ') s else s + c
    }
    new SearchTerms(cleaned)
  }

  def apply(value: String): SearchTerms = clean(value)
}