package BLL.Services

import BLL.Converters
import BLL.Models.LeaveModel
import DAL.Traits.IVacationRepository
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._

class LeaveService @Inject()(private val vacationRepository: IVacationRepository) {
  private val timeoutDuration = 3.seconds

  def create(vacationM: LeaveModel): String = {
    val dbVacation = Converters.vacationModelToVacation(vacationM)

    val addVacationF = vacationRepository.create(dbVacation)
    val addVacation = Await.result(addVacationF, timeoutDuration)
    addVacation match {
      case Some(0) | None => "No vacation created"
      case Some(_) => "Vacation created"
    }
  }

  def delete(id: Long): Int = {
    Await.result(vacationRepository.delete(id), timeoutDuration)
  }

  def getById(id: Long): Option[LeaveModel] = {
    val opVacation = Await.result(vacationRepository.getById(id), timeoutDuration)

    opVacation match {
      case Some(vacation) => Some(Converters.vacationToVacationModel(vacation))
      case None => None
    }
  }

  def get: Seq[LeaveModel] = {
    val vacations = Await.result(vacationRepository.get, timeoutDuration)

    vacations.map(Converters.vacationToVacationModel)
  }
}
