package BLL

import BLL.Models._
import DAL.Models._

object Converters {
  def userToUserModel(user: User, userDetailM: Option[UserDetailModel]): UserModel = {
    UserModel(Some(user.id), user.firstName, user.lastName,
      user.email, user.username, None, userDetailM)
  }

  def userModelToUser(userModel: UserModel, password: String): User = {
    User(userModel.id.getOrElse(0L), userModel.firstName, userModel.lastName, userModel.email, userModel.username,
      isVerified = false, isDisabled = false, password)
  }

  def userDetailToUserDetailModel(userDetail: UserDetail, userId: Option[Long]): UserDetailModel = {
    UserDetailModel(None, description = userDetail.description,
      country = userDetail.country, religion = userDetail.religion, skin = userDetail.skin,
      hair = userDetail.hair, height = userDetail.height, weight = userDetail.weight,
      gender = userDetail.gender, age = userDetail.age, userId = userId)
  }

  def userDetailModelToUserDetail(userDetail: UserDetailModel): UserDetail = {
    UserDetail(userDetail.id.getOrElse(0L), description= userDetail.description, country = userDetail.country,
      religion = userDetail.religion, height = userDetail.height, weight = userDetail.weight, skin = userDetail.skin,
      hair = userDetail.hair, gender = userDetail.gender, age = userDetail.age, userId = userDetail.userId.getOrElse(0L))
  }

  def projectToProjectModel(project: Project,
                            oSkills: Option[Seq[SkillModel]]): ProjectModel = {
    ProjectModel(Some(project.id), project.name, project.description,
      project.url, project.startDate, project.endDate, oSkills)
  }

  def projectModelToProject(projectModel: ProjectModel): Project = {
    Project(projectModel.id.getOrElse(0L), name = projectModel.name, description = projectModel.description,
      url = projectModel.url, startDate = projectModel.startDate, endDate = projectModel.endDate)
  }

  def skillToSkillModel(skill: Skill): SkillModel = {
    SkillModel(Some(skill.id), skill.name, skill.description,
      skill.level)
  }

  def skillModelToSkill(skillModel: SkillModel): Skill = {
    Skill(skillModel.id.getOrElse(0L), name = skillModel.name, description = skillModel.description,
      level = skillModel.level)
  }

  def vacationToVacationModel(vacation: Leave): LeaveModel = {
    LeaveModel(Some(vacation.id), vacation.categoryId.toString, vacation.description,
      vacation.startDate, vacation.endDate)
  }

  def vacationModelToVacation(vacationModel: LeaveModel): Leave = {
    Leave(vacationModel.id.getOrElse(0L), description = vacationModel.description, categoryId = 0L,
      startDate = vacationModel.startDate, endDate = vacationModel.endDate)
  }
}
