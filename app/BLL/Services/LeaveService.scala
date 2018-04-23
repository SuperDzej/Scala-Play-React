package BLL.Services

import BLL.Converters
import BLL.Models.{LeaveModel, OperationResult}
import DAL.Models.LeaveCategory
import DAL.Traits.{ILeaveCategoryRepository, ILeaveRepository}
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

class LeaveService @Inject()(private val leaveRepository: ILeaveRepository,
                             private val leaveCategoryRepository: ILeaveCategoryRepository) {
  private val timeoutDuration = 3.seconds

  def create(leaveM: LeaveModel): OperationResult[Long] = {
    val oCategoryId = Try(leaveM.category.toLong).toOption
    oCategoryId.fold(OperationResult(isSuccess = false,
      message = "Invalid category id", result = 0L))(categoryId => {
      val dbVacation = Converters.leaveModelToLeave(leaveM, categoryId)

      val addVacationF = leaveRepository.create(dbVacation)
      val addVacation = Await.result(addVacationF, timeoutDuration)
      addVacation match {
        case Some(0) | None => OperationResult(isSuccess = false,
          message = "No leave created", result = 0L)
        case Some(leaveId) => OperationResult(isSuccess = true,
          message = "Leave created", result = leaveId)
      }
    })
  }

  def delete(id: Long): Int = {
    Await.result(leaveRepository.delete(id), timeoutDuration)
  }

  def getById(id: Long): Option[LeaveModel] = {
    val opVacation = Await.result(leaveRepository.getById(id), timeoutDuration)

    opVacation match {
      case Some(leave) =>
        val category = Await.result(leaveCategoryRepository.getById(leave.categoryId), timeoutDuration)
        val categoryName = category.getOrElse(LeaveCategory(0L, "No category")).name
        Some(Converters.leaveToLeaveModel(leave, categoryName))
      case None => None
    }
  }

  def get: Seq[LeaveModel] = {
    val leaves = Await.result(leaveRepository.get, timeoutDuration)
    leaves.map(leaveWithCategory => {
      Converters.leaveToLeaveModel(leaveWithCategory._1, leaveWithCategory._2.name)
    })
  }
}
