import filters.AddHeaderFilter
import javax.inject.{Inject, Singleton}
import play.api.http.HttpFilters
import play.filters.csrf.CSRFFilter
import play.filters.headers.SecurityHeadersFilter

@Singleton
class Filters @Inject()(addHeaderFilter: AddHeaderFilter, csrFilter: CSRFFilter, securityHeadersFilter: SecurityHeadersFilter) extends HttpFilters {

  override val filters = Seq(addHeaderFilter, csrFilter, securityHeadersFilter)

}
