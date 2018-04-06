package DAL.Traits

import DAL.Helpers.OperationResult
import DAL.Models.UserInterest

import scala.concurrent.Future

trait IUserInterestRepository {
  def create(user: UserInterest): Future[OperationResult[UserInterest]]

  def delete(id: Long): Future[Int]

  def update(user: UserInterest) : Future[OperationResult[UserInterest]]

  def getById(id: Long): Future[Option[UserInterest]]

  def get: Future[Seq[UserInterest]]
}
