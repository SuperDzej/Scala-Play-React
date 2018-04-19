package DAL.Traits

import DAL.Models.Leave

import scala.concurrent.Future

trait IVacationRepository {
  def create(vacation: Leave): Future[Option[Long]]

  def delete(id: Long): Future[Int]

  def getById(id: Long): Future[Option[Leave]]

  def get: Future[Seq[Leave]]
}
