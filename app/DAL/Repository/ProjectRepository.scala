package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.TableMapping._
import DAL.Traits.IProjectRepository

class ProjectRepository @Inject()() extends BaseRepository() with IProjectRepository {
  val projects = TableQuery[ProjectTable]
  val projectSkills = TableQuery[ProjectSkillTable]

  def create(project: Project): Future[Option[Long]] = {
    val userIdQuery = (projects returning projects.map(_.id)) += project
    runCommand(userIdQuery).map(userId => {
       Some(userId)
    }).recover {
      case _: Exception => None
    }
  }

  def createWithSkills(project: Project, skills: Seq[Long]): Future[Option[Long]] = {
    val userIdQuery = (projects returning projects.map(_.id)) += project
    val query = for {
      projectId <- userIdQuery
      _ <- projectSkills ++= skills.map(skill => ProjectSkill(projectId, skill))
    } yield projectId

    runCommand(query).map(userId => {
      Some(userId)
    }).recover {
      case _: Exception => None
    }
  }

  def update(project: Project) : Future[Option[Project]] = {
    val mapUpdateAction = projects.filter(_.id === project.id)
      .map(dbProject => (dbProject.description, dbProject.name, dbProject.startDate, dbProject.endDate))
      .update( (project.description, project.name, project.startDate, project.endDate))

    runCommand(mapUpdateAction)
      .map(updateCount => {
        if (updateCount <= 0) None
        else Some(project)
      })
      .recover {
        case _ : Exception => None
      }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(projects.filter(_.id === id).delete)
  }

  def getById(id: Long): Future[Option[Project]] = {
    runCommand(projects.filter(_.id === id).result).map(_.headOption)
  }

  def getWithOffsetAndLimit(offset: Long, limit: Long): Future[Seq[Project]] = {
    runCommand(projects.drop(offset).take(limit).result)
  }

  def get: Future[Seq[Project]] = {
    runCommand(projects.result)
  }
}
