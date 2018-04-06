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
  val users = TableQuery[UserInterestTable]

  def create(user: UserInterest): Future[OperationResult[UserInterest]] = {
    runCommand(users += user)
      .map(_ => OperationResult[UserInterest](isSuccess = false, "User interest successfully added", Some(user)))
      .recover {
      case ex: Exception => OperationResult[UserInterest](isSuccess = false, ex.getMessage, None)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: UserInterest) : Future[OperationResult[UserInterest]] = {
    runCommand(users.update(user))
      .map(_ => OperationResult[UserInterest](isSuccess = false, "User interest successfully updated", Some(user)))
      .recover {
      case ex : Exception => OperationResult[UserInterest](isSuccess = false, ex.getMessage, None)
    }
  }

  def getById(id: Long): Future[Option[UserInterest]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserInterest]] = {
    runCommand(users.result)
  }
}