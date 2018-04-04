package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.UserReport
import DAL.Traits._
import DAL.Migrations.UserReportTable

class UserReportRepository @Inject()() extends BaseRepository() with IUserReportRepository {
  val users = TableQuery[UserReportTable]

  def add(user: UserReport): Future[String] = {

    runCommand(users += user).map(res => "User detail successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: UserReport) : Future[String] = {
    runCommand(users.update(user)).map(res => "User detail successfully updated").recover {
      case ex : Exception => ex.getCause.getMessage
    }
  }

  def getById(id: Long): Future[Option[UserReport]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserReport]] = {
    runCommand(users.result)
  }
}

