package BLL.Services

import BLL.Converters
import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.Await
import BLL.Models._
import DAL.Traits._

class ProjectService @Inject()(private val projectRepository: IProjectRepository) {
  private val timeoutDuration = 3.seconds

  def create(projectM: ProjectModel): String = {
      val dbProject = Converters.projectModelToProject(projectM)

      val addProjectF = projectRepository.create(dbProject)
      val addProject = Await.result(addProjectF, timeoutDuration)
      addProject match {
        case Some(0L) | None => "No project created"
        case Some(_) => "Project created"
      }
  }

  def addSkills(projectId: Long, skills: Seq[Long]): String = {
    val oProject = Await.result(projectRepository.getById(projectId), timeoutDuration)

    oProject match {
      case Some(project) =>
        val addProjectSkillF = projectRepository.addSkills(project._1, skills)
        val addProjectSkill = Await.result(addProjectSkillF, timeoutDuration)
        addProjectSkill match {
          case Some(0L) | None => "No project skills added"
          case Some(_) => "Project skills added"
        }
      case None => "No project with specified id"
    }
  }

  def delete(id: Long): Int = {
    Await.result(projectRepository.delete(id), timeoutDuration)
  }

  def getById(id: Long): Option[ProjectModel] = {
    val opUser = Await.result(projectRepository.getById(id), timeoutDuration)

    opUser match {
      case Some(project) =>
        val skills = project._2.map(Converters.skillToSkillModel)
        Some(Converters.projectToProjectModel(project._1, Some(skills)))
      case None => None
    }
  }

  def get: Seq[ProjectModel] = {
    val projects = Await.result(projectRepository.get, timeoutDuration)
    projects.map(project => {
      val skills = project._2.map(Converters.skillToSkillModel)
      Converters.projectToProjectModel(project._1, Some(skills))
    })
  }
}
