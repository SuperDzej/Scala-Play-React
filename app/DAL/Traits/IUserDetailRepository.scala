package DAL.Traits

import DAL.Models.UserDetail

import scala.concurrent.Future

trait IUserDetailRepository {
  def create(user: UserDetail): Future[String]

  def delete(id: Long): Future[Int]

  def update(user: UserDetail) : Future[String]

  def getById(id: Long): Future[Option[UserDetail]]

  def get: Future[Seq[UserDetail]]
}
