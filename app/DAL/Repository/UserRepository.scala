package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.User
import DAL.Traits._
import DAL.Migrations.UserTableDef

class UserRepository @Inject()() extends BaseRepository() with IUserRepository {
  val users = TableQuery[UserTableDef]

  def add(user: User): Future[String] = {

    runCommand(users += user).map(res => "User successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def getById(id: Long): Future[Option[User]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def getByEmail(email: String): Future[Option[User]] = {
    runCommand(users.filter(_.email === email).result).map(_.headOption)
  }

  def get: Future[Seq[User]] = {
    runCommand(users.result)
  }
}

