package DAL.Traits

import DAL.Models.UserLeave

import scala.concurrent.Future

trait IUserLeaveRepository {
  def deleteByUserId(id: Long): Future[Int]
  def deleteByLeaveId(id: Long): Future[Int]
  def deleteByUserAndLeaveId(userId: Long, leaveId: Long): Future[Int]
  def getByUserAndLeaveId(userId: Long, leaveId: Long): Future[Option[UserLeave]]
  def getByUserId(userId: Long): Future[Seq[UserLeave]]
  def getByLeaveId(leaveId: Long): Future[Seq[UserLeave]]
  def create(userLeave: UserLeave): Future[Int]
  def createMultiple(skills: Seq[UserLeave]): Future[Int]
}
