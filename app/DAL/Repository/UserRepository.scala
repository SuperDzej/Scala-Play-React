package DAL.Repository

import DAL.Helpers.OperationResult

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.User
import DAL.Traits._
import DAL.Migrations.UserTable

class UserRepository @Inject()() extends BaseRepository() with IUserRepository {
  val users = TableQuery[UserTable]

  def create(user: User): Future[OperationResult] = {
    println(users += user)
    runCommand(users += user)
      .map(_ => OperationResult(isSuccess = true, "User successfully added"))
      .recover {

        case ex: Exception => println(ex);OperationResult(isSuccess = false, ex.getCause.getMessage)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: User) : Future[OperationResult] = {
    runCommand(users.update(user))
      .map(_ => OperationResult(isSuccess = true, "User successfully updated"))
      .recover {
        case ex : Exception => OperationResult(isSuccess = false, ex.getCause.getMessage)
    }
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

