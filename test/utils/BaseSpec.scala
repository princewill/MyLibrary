package utils

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.PlaySpecification

trait BaseSpec extends PlaySpecification {
  val app: Application = GuiceApplicationBuilder().build()
}
