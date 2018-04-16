package BLL.Services

import java.util.Calendar

import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.Await
import BLL.Models._
import DAL.Models.UserReport
import DAL.Traits._

class UserReportService @Inject()(private val userReportRepository: IUserReportRepository) {
  private val timeoutDuration = 2.seconds

  def create(userReportM: UserReportModel): String = {

    val userReportedO = Await.result(userReportRepository
      .getByUserIdAndReportedUserId(userReportM.userId, userReportM.reportedUserId), timeoutDuration)

    userReportedO match {
      case Some(_) => "User report already created"
      case None =>
        val date = new java.sql.Timestamp(Calendar.getInstance.getTimeInMillis)

        val dbUser = UserReport(0L, reason = userReportM.reason, description = userReportM.description, date = date,
          userId = userReportM.userId, reportedUserId = userReportM.reportedUserId)

        val addUserReportF = userReportRepository.create(dbUser)
        val addUserReportResult = Await.result(addUserReportF,  timeoutDuration)
        addUserReportResult match {
          case Some(0) => "No user report created"
          case Some(_) => "User report created"
          case None => "No user report created"
        }
    }
  }

  def delete(id: Long): Int = {
    val deleteUserF = userReportRepository.delete(id)
    val deleteUserId = Await.result(deleteUserF,  timeoutDuration)
    deleteUserId
  }

  def getById(id: Long): Option[UserReportModel] = {
    val opUser = Await.result(userReportRepository.getById(id),  timeoutDuration)

    opUser match {
      case Some(dbUser) =>
        val user:Option[UserReportModel] = Some(UserReportModel(Some(dbUser.id), dbUser.reason, dbUser.description,
          Some(dbUser.date.toString), dbUser.userId, dbUser.reportedUserId))
        user
      case None => None
    }
  }

  def get: Seq[UserReportModel] = {
    val dbUsers = Await.result(userReportRepository.get,  timeoutDuration)

    val users = dbUsers.map(user => UserReportModel(Some(user.id), user.reason, user.description,
      Some(user.date.toString), user.userId, user.reportedUserId))
    users
  }
}
