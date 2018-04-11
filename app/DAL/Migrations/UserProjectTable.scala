package DAL.Migrations

import slick.jdbc.PostgresProfile.api._
import DAL.Models.UserProject

class UserProjectTable(tag: Tag) extends Table[UserProject](tag, "project_skill") {
  val projects = TableQuery[ProjectTable]
  val skills = TableQuery[SkillTable]

  def userId :Rep[Long] = column[Long]("projectId")
  def user = foreignKey("projectId_fk", userId, projects)(_.id,
    onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  def projectId: Rep[Long] = column[Long]("skillId")
  def project = foreignKey("skillId_fk", projectId, skills)(_.id)

  override def * =
    (userId, projectId) <>(UserProject.tupled, UserProject.unapply)
}
