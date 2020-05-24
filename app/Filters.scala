import filters.AddHeaderFilter
import javax.inject.{Inject, Singleton}
import play.api.http.HttpFilters

@Singleton
class Filters @Inject()(addHeaderFilter: AddHeaderFilter) extends HttpFilters {

  override val filters = Seq(addHeaderFilter)

}
