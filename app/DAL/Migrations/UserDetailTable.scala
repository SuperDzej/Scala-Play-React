package DAL.Migrations

import slick.jdbc.PostgresProfile.api._
import DAL.Models.UserDetail

class UserDetailTable (tag: Tag) extends Table[UserDetail](tag, "user_detail") {
  val users = TableQuery[UserTable]

  def id = column[Long]("id", O.PrimaryKey,O.AutoInc)
  def description = column[String]("description")
  def country = column[String]("country")
  def religion = column[String]("religion")
  def height = column[Double]("height")
  def weight = column[Double]("weight")
  def skin = column[String]("skin")
  def hair = column[String]("hair")
  def gender = column[String]("gender")
  def birthYear = column[Int]("birth_year")
  def birthMonth = column[Int]("birth_month")
  def birthDay = column[Int]("birth_day")

  def userId = column[Long]("userId")
  def user = foreignKey("userId_fk", userId, users)(_.id)

  override def * =
    (id, description, country, religion, height, weight, skin, hair,
      gender,birthYear, birthMonth, birthDay, userId) <>(UserDetail.tupled, UserDetail.unapply)
}
