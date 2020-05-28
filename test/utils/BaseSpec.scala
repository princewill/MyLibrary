package utils

import org.specs2.mock.mockito.MocksCreation
import org.specs2.specification.Scope
import play.api.i18n.{DefaultLangs, Lang}
import play.api.mvc.{BaseControllerHelpers, ControllerComponents, DefaultActionBuilderImpl, PlayBodyParsers}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global

trait BaseSpec extends ControllerComponents with BaseControllerHelpers with Scope with MocksCreation {
  override val executionContext: ExecutionContext = global
  override val parsers: PlayBodyParsers = PlayBodyParsers()(TestHelper.materializer)
  override val actionBuilder = new DefaultActionBuilderImpl(parsers.default)
  override val langs = new DefaultLangs(Seq(Lang("en", "CA")))
  override implicit val controllerComponents: ControllerComponents = this

}
