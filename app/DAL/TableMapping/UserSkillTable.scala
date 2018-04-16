package DAL.TableMapping

import slick.jdbc.PostgresProfile.api._
import DAL.Models.{UserProject, UserSkill}

class UserSkillTable(tag: Tag) extends Table[UserSkill](tag, "user_skill") {
  val users = TableQuery[UserTable]
  val skills = TableQuery[SkillTable]

  def userId :Rep[Long] = column[Long]("projectId")
  def userFK = foreignKey("projectId_fk", userId, users)(_.id,
    onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  def skillId: Rep[Long] = column[Long]("skillId")
  def skillFK = foreignKey("skillId_fk", skillId, skills)(_.id)

  override def * =
    (userId, skillId) <>(UserSkill.tupled, UserSkill.unapply)
}
