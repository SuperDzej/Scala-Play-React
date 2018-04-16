package DAL.TableMapping

import slick.jdbc.PostgresProfile.api._
import DAL.Models.Role

class RoleTable(tag: Tag) extends Table[Role](tag, "role") {
  def id = column[Long]("id")
  def name = column[String]("name")

  override def * =
    (id, name) <>(Role.tupled, Role.unapply)
}