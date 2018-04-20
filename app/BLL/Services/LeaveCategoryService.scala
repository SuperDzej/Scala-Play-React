package BLL.Services

import BLL.Converters
import BLL.Models.LeaveCategoryModel
import DAL.Repository.LeaveCategoryRepository
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._

class LeaveCategoryService @Inject()(leaveCategoryRepository: LeaveCategoryRepository) {
  private val timeoutDuration = 2.seconds

  def create(leaveCategoryM: LeaveCategoryModel): Long = {
    val dbSkill = Converters.leaveCategoryModelToLeaveCategory(leaveCategoryM)

    val addSkillF = leaveCategoryRepository.create(dbSkill)
    val addSkill = Await.result(addSkillF, timeoutDuration)
    addSkill match {
      case Some(0) | None => 0L
      case Some(categoryId) => categoryId
    }
  }

  def delete(id: Long): Int = {
    Await.result(leaveCategoryRepository.delete(id), timeoutDuration)
  }

  def getById(id: Long): Option[LeaveCategoryModel] = {
    val opSkill = Await.result(leaveCategoryRepository.getById(id), timeoutDuration)

    opSkill match {
      case Some(leaveCategory) => Some(Converters.leaveCategoryToLeaveCategoryModel(leaveCategory))
      case None => None
    }
  }

  def get: Seq[LeaveCategoryModel] = {
    val skills = Await.result(leaveCategoryRepository.get, timeoutDuration)
    skills.map(Converters.leaveCategoryToLeaveCategoryModel)
  }
}
