package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.TableMapping._
import DAL.Traits.IVacationRepository

class VacationRepository @Inject()() extends BaseRepository() with IVacationRepository {
  val vacations = TableQuery[VacationTable]

  def create(vacation: Vacation): Future[Option[Long]] = {
    val skillIdQuery = (vacations returning vacations.map(_.id)) += vacation
    runCommand(skillIdQuery).map(userId => {
      Some(userId)
    }).recover {
      case _: Exception => None
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(vacations.filter(_.id === id).delete)
  }

  def getById(id: Long): Future[Option[Vacation]] = {
    runCommand(vacations.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[Vacation]] = {
    runCommand(vacations.result)
  }
}
