package DAL.Repository

import DAL.Helpers.OperationResult

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.UserReport
import DAL.Traits._
import DAL.Migrations.UserReportTable

class UserReportRepository @Inject()() extends BaseRepository() with IUserReportRepository {
  val users = TableQuery[UserReportTable]

  def create(user: UserReport): Future[OperationResult[UserReport]] = {
    runCommand(users += user)
      .map(_ => OperationResult[UserReport](isSuccess = false, "User report successfully added", None))
      .recover {
      case ex: Exception => OperationResult[UserReport](isSuccess = false, ex.getMessage, None)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: UserReport) : Future[OperationResult[UserReport]] = {
    runCommand(users.update(user))
      .map(_ => OperationResult[UserReport](isSuccess = false, "User report successfully updated", None))
      .recover {
      case ex : Exception => OperationResult[UserReport](isSuccess = false, ex.getMessage, None)
    }
  }

  def getById(id: Long): Future[Option[UserReport]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserReport]] = {
    runCommand(users.result)
  }
}

