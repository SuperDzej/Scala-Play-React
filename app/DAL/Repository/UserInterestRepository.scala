package DAL.Repository

import DAL.Helpers.OperationResult

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.UserInterest
import DAL.Traits._
import DAL.Migrations.UserInterestTable

class UserInterestRepository @Inject()() extends BaseRepository() with IUserInterestRepository {
  val usersInterests = TableQuery[UserInterestTable]

  def create(user: UserInterest): Future[OperationResult[UserInterest]] = {
    runCommand(usersInterests += user)
      .map(_ => OperationResult[UserInterest](isSuccess = true, "User interest successfully added", Some(user)))
      .recover {
      case ex: Exception => OperationResult[UserInterest](isSuccess = false, ex.getMessage, None)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(usersInterests.filter(_.id === id).delete)
  }

  def update(user: UserInterest) : Future[OperationResult[UserInterest]] = {
    runCommand(usersInterests.update(user))
      .map(updateCount => {
        if (updateCount <= 0) {
          OperationResult(isSuccess = false, "User interests not updated", operationObject = Some(user))
        } else {
          OperationResult(isSuccess = true, "User interests successfully updated", operationObject = Some(user))
        }
      })
      .recover {
      case ex : Exception => OperationResult[UserInterest](isSuccess = false, ex.getMessage, None)
    }
  }

  def getById(id: Long): Future[Option[UserInterest]] = {
    runCommand(usersInterests.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserInterest]] = {
    runCommand(usersInterests.result)
  }
}