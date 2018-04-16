package DAL.Traits

import DAL.Models.UserInterest

import scala.concurrent.Future

trait IUserInterestRepository {
  def create(user: UserInterest): Future[Option[Long]]

  def delete(id: Long): Future[Int]

  def update(user: UserInterest) : Future[Option[UserInterest]]

  def getById(id: Long): Future[Option[UserInterest]]

  def get: Future[Seq[UserInterest]]
}
