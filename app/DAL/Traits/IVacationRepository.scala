package DAL.Traits

import DAL.Models.Vacation

import scala.concurrent.Future

trait IVacationRepository {
  def create(vacation: Vacation): Future[Option[Long]]

  def delete(id: Long): Future[Int]

  def getById(id: Long): Future[Option[Vacation]]

  def get: Future[Seq[Vacation]]
}
