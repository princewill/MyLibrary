package model.services.googleBooks

import akka.http.scaladsl.model.Uri
import javax.inject.Inject
import model.utils.SearchTerms

import scala.concurrent.{ExecutionContext, Future}

class GoogleBooksService @Inject()(serviceExecutor: GoogleBooksServiceExecutor) {

  def getVolume(searchTerm: String)(implicit ec: ExecutionContext): Future[GoogleBooksResponse] = {
    val st = SearchTerms(searchTerm)
    val path = Uri.Path(GoogleBooksService.Path)
    val query = Uri.Query(Map(("q", st.toString), ("maxResults", GoogleBooksService.MaxResults.toString)))
    val url = Uri(GoogleBooksService.Url).withPath(path)
    serviceExecutor.execute(url, query)(GoogleBooksResponse(_))
  }


}

object GoogleBooksService {

  //TODO move to config file and wrap in Uri
  val Url = "https://www.googleapis.com"
  val Path = "/books/v1/volumes"
  val MaxResults = 1
}
