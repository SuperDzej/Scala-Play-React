package DAL.Traits

import DAL.Helpers.OperationResult
import DAL.Models.UserReport

import scala.concurrent.Future

trait IUserReportRepository {
  def create(user: UserReport): Future[OperationResult[UserReport]]

  def delete(id: Long): Future[Int]

  def update(user: UserReport): Future[OperationResult[UserReport]]

  def getByUserIdAndReportedUserId(userId: Long, reportedUserId: Long): Future[Option[UserReport]]

  def getById(id: Long): Future[Option[UserReport]]

  def get: Future[Seq[UserReport]]
}
