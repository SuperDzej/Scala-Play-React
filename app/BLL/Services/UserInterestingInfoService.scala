package BLL.Services

import BLL.Converters
import BLL.Models.SkillModel
import DAL.Repository.UserInterestRepository
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._

class UserInterestingInfoService @Inject()(interestingInfoRepository: UserInterestRepository) {
  private val timeoutDuration = 2.seconds

  /*def create(skillM: ): Long = {
    val dbSkill = Converters.skillModelToSkill(skillM)

    val addSkillF = interestingInfoRepository.create(dbSkill)
    val addSkill = Await.result(addSkillF, timeoutDuration)
    addSkill match {
      case Some(0) | None => 0L
      case Some(skillId) => skillId
    }
  }
*/
  def delete(id: Long): Int = {
    Await.result(interestingInfoRepository.delete(id), timeoutDuration)
  }
/*
  def getById(id: Long): Option[SkillModel] = {
    val opSkill = Await.result(interestingInfoRepository.getById(id), timeoutDuration)

    opSkill match {
      case Some(skill) => Some(Converters.skillToSkillModel(skill, None))
      case None => None
    }
  }

  def get: Seq[SkillModel] = {
    val skills = Await.result(interestingInfoRepository.get, timeoutDuration)
    skills.map(Converters.skillToSkillModel(_, None))
  }*/
}
