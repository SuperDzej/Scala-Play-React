package DAL.Migrations

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import DAL.Models.Project

class ProjectTable(tag: Tag) extends Table[Project](tag, "project") {
  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")
  def description = column[String]("description")
  def url = column[String]("url")
  def startDate = column[Timestamp]("startDate")
  def endDate = column[Timestamp]("endDate")

  override def * =
    (id, name, description, url, startDate, endDate) <>(Project.tupled, Project.unapply)
}