package DAL.TableMapping

import slick.jdbc.PostgresProfile.api._
import DAL.Models.UserLeave

class UserLeaveTable(tag: Tag) extends Table[UserLeave](tag, "project_skill") {
  val users = TableQuery[UserTable]
  val leaves = TableQuery[LeaveTable]

  def userId :Rep[Long] = column[Long]("userId")
  def userFK = foreignKey("userId_fk", userId, users)(_.id,
    onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  def leaveId: Rep[Long] = column[Long]("leaveId")
  def leaveFk = foreignKey("leaveId_fk", leaveId, leaves)(_.id)

  override def * =
    (userId, leaveId) <>(UserLeave.tupled, UserLeave.unapply)
}
