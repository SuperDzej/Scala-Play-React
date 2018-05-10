package DAL.TableMapping

import slick.jdbc.PostgresProfile.api._
import DAL.Models.User

sealed trait Bool
case object True extends Bool
case object False extends Bool

class UserTable(tag: Tag) extends Table[User](tag, "user") {
  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def email = column[String]("email", O.SqlType("VARCHAR(50)"), O.Unique)
  def isVerified = column[Boolean]("isVerified")
  def isDisabled = column[Boolean]("isDisabled")
  def password = column[String]("password")

  def idxEmail = index("idx_email", email, unique = true)

  override def * =
    (id, firstName, lastName, email, isVerified, isDisabled, password) <>(User.tupled, User.unapply)
}
