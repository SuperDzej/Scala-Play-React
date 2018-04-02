package DAL.Repository


import javax.inject._

import play.api.Play
// import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.driver.PostgresDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

import DAL.Traits._

class BaseRepository () {
  // We get config by name from application.config where db url is specified
  val db = Database.forConfig("db")
  
  def getDatabaseConnection(): JdbcProfile#Backend#Database = {
    println("Got db: " + db)
    db
  }
}
