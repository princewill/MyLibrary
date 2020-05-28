package utils

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.{AnyContent, BodyParser, ControllerComponents}
import play.api.test.{Helpers, PlaySpecification}

trait BaseSpec extends PlaySpecification {
  val app: Application = GuiceApplicationBuilder().build()
  val parsers: BodyParser[AnyContent] = Helpers.stubBodyParser(AnyContent(""))
  implicit val controllerComponents: ControllerComponents = Helpers.stubControllerComponents(parsers)
}
