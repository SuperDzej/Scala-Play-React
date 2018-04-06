package BLL.Services

import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.Await
import BLL.Models._
import DAL.Models.UserReport
import DAL.Traits._

class UserReportService @Inject()(ussr: IUserReportRepository) {
  private val userRepository: IUserReportRepository = ussr
  private val timeoutDuration = 2.seconds

  /*def create(user: UserReportModel): String = {
    user.password.fold("No user password provided")(password => {
      val dbUser = UserReport(0L, reason= String, description= String, date= String,
        userId= Long, reportedUserId= Long)

      val addUserF = userRepository.create(dbUser)
      val addUserResult = Await.result(addUserF,  timeoutDuration)
      addUserResult.message
    })
  }

  def delete(id: Long): Int = {
    val deleteUserF = userRepository.delete(id)
    val deleteUserId = Await.result(deleteUserF,  timeoutDuration)
    deleteUserId
  }

  def getById(id: Long): Option[UserReportModel] = {
    val opUser = Await.result(userRepository.getById(id),  timeoutDuration)

    opUser match {
      case Some(dbUser) =>
        val user:Option[UserReportModel] = Some(UserReportModel(dbUser.id, dbUser.firstName, dbUser.lastName,
          dbUser.email, dbUser.username, None))
        user
      case None => None
    }
  }

  def get: Seq[UserReportModel] = {
    val dbUsers = Await.result(userRepository.get,  timeoutDuration)

    val users = dbUsers.map(user => UserReportModel(user.id, user.firstName, user.lastName,
      user.email, user.username, None))
    users
  }*/
}
