package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.UserDetail
import DAL.Traits._
import DAL.Migrations.UserDetailTable

class UserDetailRepository @Inject()() extends BaseRepository() with IUserDetailRepository {
  val users = TableQuery[UserDetailTable]

  def create(user: UserDetail): Future[String] = {

    runCommand(users += user).map(_ => "User detail successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: UserDetail) : Future[String] = {
    runCommand(users.update(user)).map(_ => "User detail successfully updated").recover {
      case ex : Exception => ex.getCause.getMessage
    }
  }

  def getById(id: Long): Future[Option[UserDetail]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserDetail]] = {
    runCommand(users.result)
  }
}

