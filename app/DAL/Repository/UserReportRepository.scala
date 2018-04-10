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
      .map(_ => OperationResult[UserReport](isSuccess = true, "User report successfully added", None))
      .recover {
      case ex: Exception => OperationResult[UserReport](isSuccess = false, ex.getMessage, None)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: UserReport) : Future[OperationResult[UserReport]] = {
    val mapUpdateAction = users.filter(_.id === user.id)
      .map(dbUser => (dbUser.reason, dbUser.description, dbUser.date))
      .update( (user.reason, user.description, user.date))

    runCommand(mapUpdateAction)
      .map(updateCount => {
        if (updateCount <= 0) {
          OperationResult(isSuccess = false, "User report not updated", operationObject = Some(user))
        } else {
          OperationResult(isSuccess = true, "User report successfully updated", operationObject = Some(user))
        }
      })
      .recover {
      case ex : Exception => OperationResult[UserReport](isSuccess = false, ex.getMessage, None)
    }
  }

  def getByUserIdAndReportedUserId(userId: Long, reportedUserId: Long) : Future[Option[UserReport]] = {
    runCommand(users.filter(user => user.userId === userId &&
      user.reportedUserId === reportedUserId).result)
      .map(_.headOption)
  }

  def getById(id: Long): Future[Option[UserReport]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserReport]] = {
    runCommand(users.result)
  }
}

