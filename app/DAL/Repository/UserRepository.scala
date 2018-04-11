package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject._

import DAL.Helpers.OperationResult
import DAL.Models.User
import DAL.Traits._
import DAL.Migrations.UserTable

class UserRepository @Inject()() extends BaseRepository() with IUserRepository {
  val users = TableQuery[UserTable]

  def create(user: User): Future[OperationResult[Long]] = {
    val userIdQuery = (users returning users.map(_.id)) += user
    runCommand(userIdQuery).map(userId => {
      OperationResult[Long](isSuccess = true, "User successfully created", Some(userId))
    }).recover {
        case ex: Exception => OperationResult[Long](isSuccess = false, ex.getMessage, None)
      }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: User) : Future[OperationResult[User]] = {
    val mapUpdateAction = users.filter(_.id === user.id)
      .map(dbUser => (dbUser.firstName, dbUser.lastName, dbUser.username, dbUser.isVerified, dbUser.isDisabled))
      .update( (user.firstName, user.lastName, user.username, user.isVerified, user.isDisabled))

    runCommand(mapUpdateAction)
      .map(updateCount => {
        if (updateCount <= 0) {
          OperationResult(isSuccess = false, "User not updated", operationObject = Some(user))
        } else {
          OperationResult(isSuccess = true, "User successfully updated", operationObject = Some(user))
        }
      })
      .recover {
        case ex : Exception => OperationResult[User](isSuccess = false, ex.getMessage, None)
    }
  }

  def getById(id: Long): Future[Option[User]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def getByEmail(email: String): Future[Option[User]] = {
    runCommand(users.filter(_.email === email).result).map(_.headOption)
  }

  def getWithOffsetAndLimit(offset: Long, limit: Long): Future[Seq[User]] = {
    runCommand(users.drop(offset).take(limit).result)
  }

  def get: Future[Seq[User]] = {
    runCommand(users.result)
  }
}

