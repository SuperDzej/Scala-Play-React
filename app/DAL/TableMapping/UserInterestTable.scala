package DAL.TableMapping

import slick.jdbc.PostgresProfile.api._
import DAL.Models.UserInterestingInfo

class UserInterestTable (tag: Tag) extends Table[UserInterestingInfo](tag, "user_interest") {
  val users = TableQuery[UserTable]

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("description")
  def name = column[String]("name")

  def userId = column[Long]("userId")
  def user = foreignKey("userId_fk", userId, users)(_.id)

  override def * =
    (id, name, description, userId) <>(UserInterestingInfo.tupled, UserInterestingInfo.unapply)
}