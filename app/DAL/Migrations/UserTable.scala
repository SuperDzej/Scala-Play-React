package DAL.Migrations

import slick.jdbc.PostgresProfile.api._
import DAL.Models.User

sealed trait Bool
case object True extends Bool
case object False extends Bool

class UserTable(tag: Tag) extends Table[User](tag, "user") {
  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def email = column[String]("email")
  def username = column[String]("username")
  def isVerified = column[Short]("isVerified")
  def isDisabled = column[Short]("isDisabled")
  def password = column[String]("password")

  override def * =
    (id, firstName, lastName, email, username, isVerified, isDisabled, password) <>(User.tupled, User.unapply)
}
