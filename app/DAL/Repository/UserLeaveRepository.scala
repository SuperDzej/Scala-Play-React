package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.Traits._

class UserLeaveRepository @Inject()() extends BaseRepository() with IUserLeaveRepository {
  def deleteByUserId(id: Long): Future[Int] = {
    runCommand(userLeaves.filter(_.userId === id).delete)
  }

  def deleteByLeaveId(id: Long): Future[Int] = {
    runCommand(userLeaves.filter(_.leaveId === id).delete)
  }

  def deleteByUserAndLeaveId(userId: Long, leaveId: Long): Future[Int] = {
    runCommand(userLeaves.filter(userLeave => userLeave.leaveId === leaveId &&
      userLeave.userId === userId).delete)
  }

  def getByUserAndLeaveId(userId: Long, leaveId: Long): Future[Option[UserLeave]] = {
    runCommand(userLeaves.filter(userLeave => userLeave.userId === userId &&
      userLeave.leaveId === leaveId).result).map(_.headOption)
  }

  def getByUserId(userId: Long): Future[Seq[UserLeave]] = {
    runCommand(userLeaves.filter(_.userId === userId).result)
  }

  def getByLeaveId(leaveId: Long): Future[Seq[UserLeave]] = {
    runCommand(userLeaves.filter(_.leaveId === leaveId).result)
  }

  def getLeavesByUserId(userId: Long) : Future[Seq[(Leave, LeaveCategory)]] = {
    val join = userLeaves
      .join(users).on(_.userId === _.id)
      .join(leaves).on(_._1.leaveId === _.id)
      .join(leaveCategories).on(_._2.categoryId === _.id)
      .filter(_._1._1._2.id === userId)
      .result

    runCommand(join)
      .map(_.map(ulTuple => (ulTuple._1._2, ulTuple._2)))
  }

  def create(userLeave: UserLeave): Future[Int] = {
    val query = for {
      addCount <- userLeaves += userLeave
    } yield addCount

    runCommand(query)
  }

  def createMultiple(userLeaves: Seq[UserLeave]): Future[Int] = {
    val query = for {
      addCount <- this.userLeaves ++= userLeaves
    } yield addCount

    runCommand(query).map(addedCount => {
      addedCount.getOrElse(0)
    }).recover {
      case e: Exception => println("Ex: " + e.getLocalizedMessage); 0
    }
  }
}
