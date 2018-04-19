package BLL.Services

import BLL.Converters
import BLL.Models.SkillModel
import DAL.Repository.SkillRepository
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._

class SkillService @Inject()(skillRepository: SkillRepository) {
  private val timeoutDuration = 2.seconds

  def create(skillM: SkillModel): String = {
    val dbSkill = Converters.skillModelToSkill(skillM)

    val addSkillF = skillRepository.create(dbSkill)
    val addSkill = Await.result(addSkillF, timeoutDuration)
    addSkill match {
      case Some(0) | None => "No skill created"
      case Some(_) => "Skill created"
    }
  }

  def delete(id: Long): Int = {
    Await.result(skillRepository.delete(id), timeoutDuration)
  }

  def getById(id: Long): Option[SkillModel] = {
    val opSkill = Await.result(skillRepository.getById(id), timeoutDuration)

    opSkill match {
      case Some(skill) => Some(Converters.skillToSkillModel(skill))
      case None => None
    }
  }

  def get: Seq[SkillModel] = {
    val projects = Await.result(skillRepository.get, timeoutDuration)
    projects.map(Converters.skillToSkillModel)
  }
}
