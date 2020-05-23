package controllers

import javax.inject.Inject
import play.api.mvc.{BaseController, ControllerComponents, ControllerHelpers}


@Singleton
class MyBook @Inject()(val controllerComponents: ControllerComponents) extends BaseController with ControllerHelpers {

  def addBook
  def addBooks
  def getBooks
  def getBook
  def deleteBook
  def deleteAll
  def updateBook

}
