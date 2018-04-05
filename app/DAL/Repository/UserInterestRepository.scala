package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.UserInterest
import DAL.Traits._
import DAL.Migrations.UserInterestTable

class UserInterestRepository @Inject()() extends BaseRepository() with IUserInterestRepository {
  val users = TableQuery[UserInterestTable]

  def create(user: UserInterest): Future[String] = {
    runCommand(users += user).map(_ => "User interest successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: UserInterest) : Future[String] = {
    runCommand(users.update(user)).map(_ => "User interest successfully updated").recover {
      case ex : Exception => ex.getCause.getMessage
    }
  }

  def getById(id: Long): Future[Option[UserInterest]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserInterest]] = {
    runCommand(users.result)
  }
}