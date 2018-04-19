package DAL.TableMapping

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import DAL.Models.Leave

class LeaveTable(tag: Tag) extends Table[Leave](tag, "leave") {
  val leaveCategories = TableQuery[LeaveCategoryTable]
  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def description = column[String]("description")
  def startDate = column[Timestamp]("startDate")
  def endDate = column[Timestamp]("endDate")
  def categoryId = column[Long]("categoryId")
  def category = foreignKey("categoryId_fk", categoryId, leaveCategories)(_.id)

  override def * =
    (id, description, categoryId, startDate, endDate) <>(Leave.tupled, Leave.unapply)
}
