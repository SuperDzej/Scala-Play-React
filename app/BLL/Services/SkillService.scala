package BLL.Services

import BLL.Models.SkillModel
import DAL.Models.Skill
import DAL.Repository.SkillRepository
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._

class SkillService @Inject()(skillRepository: SkillRepository) {
  private val timeoutDuration = 2.seconds

  def create(skillM: SkillModel): String = {
    val dbSkill = Skill(0L, name = skillM.name, description = skillM.description,
      level = skillM.level)

    val addUserReportF = skillRepository.create(dbSkill)
    val addUserReportResult = Await.result(addUserReportF, timeoutDuration)
    addUserReportResult match {
      case Some(0) => "No skill created"
      case Some(_) => "Skill created"
      case None => "No skill created"
    }
  }

  def delete(id: Long): Int = {
    val deleteUserF = skillRepository.delete(id)
    val deleteUserId = Await.result(deleteUserF, timeoutDuration)
    deleteUserId
  }

  def getById(id: Long): Option[SkillModel] = {
    val opUser = Await.result(skillRepository.getById(id), timeoutDuration)

    opUser match {
      case Some(dbSkill) =>
        val skill:Option[SkillModel] = Some(SkillModel(Some(dbSkill.id), dbSkill.name, dbSkill.description,
          dbSkill.level))
        skill
      case None => None
    }
  }

  def get: Seq[SkillModel] = {
    val dbSkills = Await.result(skillRepository.get, timeoutDuration)

    val projects = dbSkills.map(project => SkillModel(Some(project.id), project.name, project.description,
      project.level))
    projects
  }
}
