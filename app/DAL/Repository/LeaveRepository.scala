package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.Traits.ILeaveRepository

class LeaveRepository @Inject()() extends BaseRepository() with ILeaveRepository {
  def create(vacation: Leave): Future[Option[Long]] = {
    val vacationIdQuery = (leaves returning leaves.map(_.id)) += vacation
    runCommand(vacationIdQuery).map(vacationId => {
      Some(vacationId)
    }).recover {
      case _: Exception => None
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(leaves.filter(_.id === id).delete)
  }

  def getById(id: Long): Future[Option[Leave]] = {
    runCommand(leaves.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[(Leave, LeaveCategory)]] = {
    val innerJoin = (for {
      (c, s) <- leaves join leaveCategories on (_.categoryId === _.id)
    } yield (c, s)).result
    runCommand(innerJoin)
  }
}
