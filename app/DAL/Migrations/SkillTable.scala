package DAL.Migrations

import java.sql.Timestamp

import slick.jdbc.PostgresProfile.api._
import DAL.Models.Skill

class SkillTable(tag: Tag) extends Table[Skill](tag, "project") {
  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def name = column[String]("name")
  def description = column[String]("description")
  def level = column[String]("level")

  override def * =
    (id, name, description, level) <>(Skill.tupled, Skill.unapply)
}
