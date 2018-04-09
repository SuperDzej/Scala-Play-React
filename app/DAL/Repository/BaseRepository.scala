package DAL.Repository

import slick.dbio.{DBIOAction, NoStream}
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future

class BaseRepository () {
  // We get config by name from application.config where db url is specified
  val db = Database.forConfig("databaseUrl")
  
  def getDatabaseConnection: JdbcProfile#Backend#Database = {
    db
  }

  def runCommand[R](command: DBIOAction[R, NoStream, Nothing]): Future[R] = {
    val db = Database.forConfig("databaseUrl")
    try{
      db.run(command)
    } finally db.close()
  }
}
