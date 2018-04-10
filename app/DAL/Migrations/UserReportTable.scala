package DAL.Migrations


import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import DAL.Models.UserReport

class UserReportTable  (tag: Tag) extends Table[UserReport](tag, "user_report") {
  val users = TableQuery[UserTable]

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def reason = column[String]("reason")
  def description = column[String]("description")
  def date = column[Timestamp]("date")

  def userId = column[Long]("userId")
  def user = foreignKey("userId_fk", userId, users)(_.id)

  def reportedUserId = column[Long]("reportedUserId")
  def reportedUser = foreignKey("reportedUserId_fk", userId, users)(_.id)

  override def * =
    (id, reason, description, date, userId, reportedUserId) <>(UserReport.tupled, UserReport.unapply)
}