package BLL.Services

import BLL.Converters.{ProjectConverter, SkillConverter}
import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.Await
import BLL.Models._
import DAL.Traits._

class ProjectService @Inject()(private val projectRepository: IProjectRepository) {
  private val timeoutDuration = 3.seconds

  def create(projectM: ProjectModel): String = {
      val dbProject = ProjectConverter.projectModelToProject(projectM)

      val addUserReportF = projectRepository.create(dbProject)
      val addUserReportResult = Await.result(addUserReportF, timeoutDuration)
      addUserReportResult match {
        case Some(0L) | None => "No project created"
        case Some(_) => "Project created"
      }
  }

  def addSkills(projectId: Long, skills: Seq[Long]): String = {
    val oProject = Await.result(projectRepository.getById(projectId), timeoutDuration)

    oProject match {
      case Some(project) =>
        val addUserReportF = projectRepository.addSkills(project._1, skills)
        val addUserReportResult = Await.result(addUserReportF, timeoutDuration)
        addUserReportResult match {
          case Some(0L) | None => "No project skills added"
          case Some(_) => "Project skills added"
        }
      case None => "No project with specified id"
    }
  }

  def delete(id: Long): Int = {
    val deleteUserF = projectRepository.delete(id)
    Await.result(deleteUserF, timeoutDuration)
  }

  def getById(id: Long): Option[ProjectModel] = {
    val opUser = Await.result(projectRepository.getById(id), timeoutDuration)

    opUser match {
      case Some(project) =>
        val skills = project._2.map(SkillConverter.skillToSkillModel)
        Some(ProjectConverter.projectToProjectModel(project._1, Some(skills)))
      case None => None
    }
  }

  def get: Seq[ProjectModel] = {
    val projects = Await.result(projectRepository.get, timeoutDuration)
    projects.map(project => {
      val skills = project._2.map(SkillConverter.skillToSkillModel)
      ProjectConverter.projectToProjectModel(project._1, Some(skills))
    })
  }
}
