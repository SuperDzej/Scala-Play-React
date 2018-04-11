package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject._

import DAL.Helpers.OperationResult
import DAL.Models._
import DAL.Migrations._

class SkillRepository @Inject()() extends BaseRepository() {
  val skills = TableQuery[SkillTable]

  def create(skill: Skill): Future[OperationResult[Long]] = {
    val userIdQuery = (skills returning skills.map(_.id)) += skill
    runCommand(userIdQuery).map(userId => {
      OperationResult[Long](isSuccess = true, "Skill successfully created", Some(userId))
    }).recover {
      case ex: Exception => OperationResult[Long](isSuccess = false, ex.getMessage, None)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(skills.filter(_.id === id).delete)
  }

  def getById(id: Long): Future[Option[Skill]] = {
    runCommand(skills.filter(_.id === id).result).map(_.headOption)
  }

  def getWithOffsetAndLimit(offset: Long, limit: Long): Future[Seq[Skill]] = {
    runCommand(skills.drop(offset).take(limit).result)
  }

  def get: Future[Seq[Skill]] = {
    runCommand(skills.result)
  }
}
