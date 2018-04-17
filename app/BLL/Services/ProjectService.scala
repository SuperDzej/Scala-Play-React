package BLL.Services

import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.Await
import BLL.Models._
import DAL.Models.Project
import DAL.Traits._

class ProjectService @Inject()(private val projectRepository: IProjectRepository) {
  private val timeoutDuration = 4.seconds

  def create(projectM: ProjectModel): String = {
      val dbUser = Project(0L, name = projectM.name, description = projectM.description,
        url = projectM.url, startDate = projectM.startDate, endDate = projectM.endDate)

      val addUserReportF = projectRepository.create(dbUser)
      val addUserReportResult = Await.result(addUserReportF,  timeoutDuration)
      addUserReportResult match {
        case Some(0L) | None => "No project created"
        case Some(_) => "Project created"
      }
  }

  def addSkills(projectId: Long, skills: Seq[Long]): String = {
    val oProject = Await.result(projectRepository.getById(projectId), timeoutDuration)

    oProject match {
      case Some(project) =>
        val addUserReportF = projectRepository.addSkills(project, skills)
        val addUserReportResult = Await.result(addUserReportF,  timeoutDuration)
        addUserReportResult match {
          case Some(0L) | None => "No project skills added"
          case Some(_) => "Project skills added"
        }
      case None => "No project with specified id"
    }
  }

  def delete(id: Long): Int = {
    val deleteUserF = projectRepository.delete(id)
    val deleteUserId = Await.result(deleteUserF,  timeoutDuration)
    deleteUserId
  }

  def getById(id: Long): Option[ProjectModel] = {
    val opUser = Await.result(projectRepository.getById(id),  timeoutDuration)

    opUser match {
      case Some(dbUser) =>
        val user:Option[ProjectModel] = Some(ProjectModel(Some(dbUser.id), dbUser.name, dbUser.description,
          dbUser.url, dbUser.startDate, dbUser.endDate, None))
        user
      case None => None
    }
  }

  def get: Seq[ProjectModel] = {
    // val dbProjects = Await.result(projectRepository.get,  timeoutDuration)

    val projectsWithSkills = Await.result(projectRepository.getWithSkills,  timeoutDuration)
    val projects = projectsWithSkills.map(project => {
      val skills = project._2.map(skill => SkillModel(id = Some(skill.id), name = skill.name,
        description =  skill.description, level = skill.level))

      ProjectModel(Some(project._1.id), project._1.name, project._1.description,
        project._1.url, project._1.startDate, project._1.endDate, Some(skills))
    })

    /*val projects = dbProjects.map(project => ProjectModel(Some(project.id), project.name, project.description,
      project.url, project.startDate, project.endDate, Seq.empty))*/
    projects
  }
}
