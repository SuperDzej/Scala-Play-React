package DAL.TableMapping

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import DAL.Models.Vacation

class VacationTable(tag: Tag) extends Table[Vacation](tag, "vacation") {
  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def category = column[String]("name")
  def description = column[String]("description")
  def startDate = column[Timestamp]("startDate")
  def endDate = column[Timestamp]("endDate")

  override def * =
    (id, description, category, startDate, endDate) <>(Vacation.tupled, Vacation.unapply)
}
