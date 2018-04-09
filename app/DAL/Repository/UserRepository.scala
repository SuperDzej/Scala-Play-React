package DAL.Repository

import DAL.Helpers.OperationResult

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.{User, UserDetail}
import DAL.Traits._
import DAL.Migrations.{UserDetailTable, UserTable}

class UserRepository @Inject()() extends BaseRepository() with IUserRepository {
  val users = TableQuery[UserTable]
  val usersDetails = TableQuery[UserDetailTable]

  def create(user: User): Future[OperationResult[User]] = {
    val userIdQuery = (users returning users.map(_.id)) += user
    runCommand(userIdQuery).map(userId => {
      user.id = userId
      OperationResult[User](isSuccess = true, "User successfully created", Some(user))
    }).recover {
        case ex: Exception => OperationResult[User](isSuccess = false, ex.getMessage, None)
      }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: User) : Future[OperationResult[User]] = {
    runCommand(users.update(user))
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

  def getWithOffsetAndLimit(offset: Int, limit: Int): Future[Seq[User]] = {
    runCommand(users.drop(offset).take(limit).result)
  }

  def getWithDetails: Future[Seq[(User, UserDetail)]] = {
    val innerJoin = (for {
      (c, s) <- users join usersDetails on (_.id === _.userId)
    } yield (c, s)).result
    runCommand(innerJoin)
  }

  def get: Future[Seq[User]] = {
    val innerJoin = (for {
      (c, s) <- users join users on (_.id === _.id)
    } yield (c, s)).result
    runCommand(innerJoin).map(se => println(se))
    runCommand(users.result)
  }
}

