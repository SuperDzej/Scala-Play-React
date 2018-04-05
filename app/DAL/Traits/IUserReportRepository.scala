package DAL.Traits

import DAL.Models.UserReport

import scala.concurrent.Future

trait IUserReportRepository {
  def create(user: UserReport): Future[String]

  def delete(id: Long): Future[Int]

  def update(user: UserReport) : Future[String]

  def getById(id: Long): Future[Option[UserReport]]

  def get: Future[Seq[UserReport]]
}
