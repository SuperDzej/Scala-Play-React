package BLL.Services


import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.Await
import BLL.Models._
import DAL.Models.Project
import DAL.Traits._

class ProjectService @Inject()(private val projectRepository: IProjectRepository) {
  private val timeoutDuration = 2.seconds

  def create(projectM: ProjectModel): String = {
      val dbUser = Project(0L, name = projectM.name, description = projectM.description,
        url = projectM.url, startDate = projectM.startDate, endDate = projectM.endDate.get)

      val addUserReportF = projectRepository.createWithSkills(dbUser, projectM.skills.map(_.id.getOrElse(0L)))
      val addUserReportResult = Await.result(addUserReportF,  timeoutDuration)
      addUserReportResult match {
        case Some(0) => "No project created"
        case Some(_) => "Project created"
        case None => "No project created"
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
          dbUser.url, dbUser.startDate, Some(dbUser.endDate), Seq.empty))
        user
      case None => None
    }
  }

  def get: Seq[ProjectModel] = {
    val dbProjects = Await.result(projectRepository.get,  timeoutDuration)

    val projects = dbProjects.map(project => ProjectModel(Some(project.id), project.name, project.description,
      project.url, project.startDate, Some(project.endDate), Seq.empty))
    projects
  }
}
