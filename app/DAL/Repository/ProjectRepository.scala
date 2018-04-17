package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.TableMapping._
import DAL.Traits.IProjectRepository

import scala.collection.mutable

class ProjectRepository @Inject()() extends BaseRepository() with IProjectRepository {
  val projects = TableQuery[ProjectTable]
  val skills = TableQuery[SkillTable]
  val projectSkills = TableQuery[ProjectSkillTable]

  def create(project: Project): Future[Option[Long]] = {
    val userIdQuery = (projects returning projects.map(_.id)) += project
    runCommand(userIdQuery).map(userId => {
       Some(userId)
    }).recover {
      case _: Exception => None
    }
  }

  def addSkills(project: Project, skills: Seq[Long]): Future[Option[Long]] = {
    val query = for {
      projectSKillsM <- projectSkills ++= skills.map(skill => ProjectSkill(skillId = skill, projectId = project.id))
    } yield projectSKillsM

    runCommand(query).map(projectSkillsAdded => {
      Some(projectSkillsAdded.get.asInstanceOf[Long])
    }).recover {
      case e: Exception => println("Ex: " + e.getLocalizedMessage); None
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

  def getWithSkills: Future[Seq[(Project, mutable.ArrayBuffer[Skill])]] = {
    val crossJoin = (for {
      (project, opSkills) <- projects join (projectSkills join skills on (_.skillId === _.id)) on (_.id === _._1.projectId)

    } yield(project, opSkills._2)).result

    runCommand(crossJoin).map(projectSkillsMapping => {

      var projectSkills = mutable.Seq[(Project, mutable.ArrayBuffer[Skill])]()
      projectSkillsMapping.map(pSkills => {
        val ele = projectSkills.filter(r => r._1.id == pSkills._1.id)
        if(ele.nonEmpty) {
          ele.head._2 += pSkills._2
        } else {
          val skills = mutable.ArrayBuffer() += pSkills._2
          projectSkills = projectSkills :+ new Tuple2[Project, mutable.ArrayBuffer[Skill]](pSkills._1, skills)
        }

        ele
      })
      projectSkills
    }).recover {
      case e: Exception => println("Ex: " + e.getLocalizedMessage); Seq.empty
    }
  }
}
