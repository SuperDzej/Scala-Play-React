package DAL.Traits

import DAL.Helpers.OperationResult
import DAL.Models.UserDetail

import scala.concurrent.Future

trait IUserDetailRepository {
  def create(user: UserDetail): Future[OperationResult[UserDetail]]

  def delete(id: Long): Future[Int]

  def update(user: UserDetail) : Future[OperationResult[UserDetail]]

  def getById(id: Long): Future[Option[UserDetail]]

  def get: Future[Seq[UserDetail]]
}
