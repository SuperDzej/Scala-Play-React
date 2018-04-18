package BLL.Services

import DAL.Traits.IVacationRepository
import javax.inject.Inject

import scala.concurrent.Await
import scala.concurrent.duration._

class VacationService @Inject()(private val vacationRepository: IVacationRepository) {
  private val timeoutDuration = 3.seconds

}
