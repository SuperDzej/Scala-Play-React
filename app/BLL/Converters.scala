package BLL

import BLL.Models._
import DAL.Models._

object Converters {
  def userToUserModel(user: User, userDetailM: Option[UserDetailModel],
                      skills: Option[Seq[SkillModel]], projects: Option[Seq[ProjectModel]],
                      leaves: Option[Seq[LeaveModel]]): UserModel = {
    UserModel(Some(user.id), user.firstName, user.lastName,
      user.email, user.username, None, userDetailM, skills = skills, projects = projects, leaves = leaves)
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

  def skillToSkillModel(skill: Skill, oUserSkill: Option[UserSkill]): SkillModel = {
    oUserSkill match {
      case Some(userSkill) => SkillModel(Some(skill.id), skill.name, skill.description,
        level = Some(userSkill.level), yearsExperience = Some(userSkill.yearsExperience))
      case None => SkillModel(Some(skill.id), skill.name, skill.description, None, None)
    }
  }

  def skillModelToSkill(skillModel: SkillModel): Skill = {
    Skill(skillModel.id.getOrElse(0L), name = skillModel.name, description = skillModel.description)
  }

  def leaveToLeaveModel(leave: Leave, categoryName: String): LeaveModel = {
    LeaveModel(Some(leave.id), category = categoryName, description = leave.description,
      isApproved = leave.isApproved, startDate = leave.startDate, endDate = leave.endDate)
  }

  def leaveModelToLeave(leaveModel: LeaveModel, categoryId: Long): Leave = {
    Leave(leaveModel.id.getOrElse(0L), description = leaveModel.description, categoryId = categoryId,
      isApproved = leaveModel.isApproved, startDate = leaveModel.startDate, endDate = leaveModel.endDate)
  }

  def leaveCategoryToLeaveCategoryModel(leave: LeaveCategory): LeaveCategoryModel = {
    LeaveCategoryModel(Some(leave.id), leave.name.toString)
  }

  def leaveCategoryModelToLeaveCategory(leaveModel: LeaveCategoryModel): LeaveCategory = {
    LeaveCategory(leaveModel.id.getOrElse(0L), name = leaveModel.name)
  }

  def interestingInfoToInterestingInfoModel(interestingInfo: UserInterestingInfo): InterestingInfoModel = {
    InterestingInfoModel(Some(interestingInfo.id), interestingInfo.name, interestingInfo.description, interestingInfo.url)
  }

  def interestingInfoModelToInterestingInfo(interestingInfoModel: InterestingInfoModel,
                                            userId: Long): UserInterestingInfo = {
    UserInterestingInfo(interestingInfoModel.id.getOrElse(0L), interestingInfoModel.name,
      interestingInfoModel.description, interestingInfoModel.url, userId)
  }
}
