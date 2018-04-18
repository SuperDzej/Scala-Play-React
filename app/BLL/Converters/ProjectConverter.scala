package BLL.Converters

import BLL.Models.{ProjectModel, SkillModel}
import DAL.Models.Project

object ProjectConverter {
  def projectToProjectModel(project: Project,
                                   oSkills: Option[Seq[SkillModel]]): ProjectModel = {
    ProjectModel(Some(project.id), project.name, project.description,
      project.url, project.startDate, project.endDate, oSkills)
  }

  def projectModelToProject(projectModel: ProjectModel): Project = {
    Project(projectModel.id.getOrElse(0L), name = projectModel.name, description = projectModel.description,
      url = projectModel.url, startDate = projectModel.startDate, endDate = projectModel.endDate)
  }
}
