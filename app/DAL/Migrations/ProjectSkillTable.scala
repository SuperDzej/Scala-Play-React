package DAL.Migrations

import slick.jdbc.PostgresProfile.api._
import DAL.Models.UserRole

class ProjectSkillTable(tag: Tag) extends Table[UserRole](tag, "project_skill") {
  val projects = TableQuery[ProjectTable]
  val skills = TableQuery[SkillTable]

  def projectId :Rep[Long] = column[Long]("projectId")
  def project = foreignKey("projectId_fk", projectId, projects)(_.id,
    onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  def skillId: Rep[Long] = column[Long]("skillId")
  def skill = foreignKey("skillId_fk", skillId, skills)(_.id)

  override def * =
    (skillId, projectId) <>(UserRole.tupled, UserRole.unapply)
}