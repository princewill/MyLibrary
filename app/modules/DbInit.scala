package modules

import javax.inject.{Inject, Singleton}
import model.utils.{InitData, Logger}
import play.api.db.evolutions.ApplicationEvolutions

@Singleton
class DbInit @Inject() (initData: InitData, applicationEvolutions: ApplicationEvolutions) extends Logger {

  if(applicationEvolutions.upToDate) {
    initData.createDBInitData()
    logger.info("initializing db with data")
  }
  else {
    initData.createDBInitData()
    logger.info("Evolutions not up to date")
  }

}
