package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.TableMapping._
import DAL.Traits.IVacationRepository

class LeaveRepository @Inject()() extends BaseRepository() with IVacationRepository {
  val vacations = TableQuery[LeaveTable]

  def create(vacation: Leave): Future[Option[Long]] = {
    val vacationIdQuery = (vacations returning vacations.map(_.id)) += vacation
    runCommand(vacationIdQuery).map(vacationId => {
      Some(vacationId)
    }).recover {
      case _: Exception => None
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(vacations.filter(_.id === id).delete)
  }

  def getById(id: Long): Future[Option[Leave]] = {
    runCommand(vacations.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[Leave]] = {
    runCommand(vacations.result)
  }
}
