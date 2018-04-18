package BLL.Services

import BLL.Converters.SkillConverter
import BLL.Models.SkillModel
import DAL.Repository.SkillRepository
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._

class SkillService @Inject()(skillRepository: SkillRepository) {
  private val timeoutDuration = 2.seconds

  def create(skillM: SkillModel): String = {
    val dbSkill = SkillConverter.skillModelToSkill(skillM)

    val addUserReportF = skillRepository.create(dbSkill)
    val addUserReportResult = Await.result(addUserReportF, timeoutDuration)
    addUserReportResult match {
      case Some(0) | None => "No skill created"
      case Some(_) => "Skill created"
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
      case Some(dbSkill) => Some(SkillConverter.skillToSkillModel(dbSkill))
      case None => None
    }
  }

  def get: Seq[SkillModel] = {
    val projects = Await.result(skillRepository.get, timeoutDuration)

    projects.map(skill => SkillConverter.skillToSkillModel(skill))
  }
}
