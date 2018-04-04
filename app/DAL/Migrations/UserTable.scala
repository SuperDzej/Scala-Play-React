package DAL.Migrations

import slick.jdbc.PostgresProfile.api._
import DAL.Models.User

class UserTable(tag: Tag) extends Table[User](tag, "user") {

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def phone = column[String]("phone")
  def email = column[String]("email")
  def username = column[String]("username")
  def isVerified = column[Boolean]("isVerified")
  def isDisabled = column[Boolean]("isDisabled")
  def password = column[String]("password")

  override def * =
    (id, firstName, lastName, phone, email, username, isVerified, isDisabled, password) <>(User.tupled, User.unapply)
}
