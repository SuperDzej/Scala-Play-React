package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.UserReport
import DAL.Traits._
import DAL.TableMapping.UserReportTable

class UserReportRepository @Inject()() extends BaseRepository() with IUserReportRepository {
  val userReports = TableQuery[UserReportTable]

  def create(userReport: UserReport): Future[Option[Long]] = {
    val userIdQuery = (userReports returning userReports.map(_.id)) += userReport
    runCommand(userIdQuery)
      .map(userId => Some(userId))
      .recover {
      case _: Exception => None
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(userReports.filter(_.id === id).delete)
  }

  def update(user: UserReport): Future[Option[UserReport]] = {
    val mapUpdateAction = userReports.filter(_.id === user.id)
      .map(dbUser => (dbUser.reason, dbUser.description, dbUser.date))
      .update( (user.reason, user.description, user.date))

    runCommand(mapUpdateAction)
      .map(updateCount => {
        if (updateCount <= 0) None
        else Some(user)
      })
      .recover {
      case _ : Exception => None
    }
  }

  def getByUserIdAndReportedUserId(userId: Long, reportedUserId: Long): Future[Option[UserReport]] = {
    runCommand(userReports.filter(user => user.userId === userId &&
      user.reportedUserId === reportedUserId).result)
      .map(_.headOption)
  }

  def getById(id: Long): Future[Option[UserReport]] = {
    runCommand(userReports.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserReport]] = {
    runCommand(userReports.result)
  }
}

