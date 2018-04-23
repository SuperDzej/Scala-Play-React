package DAL.Traits

import DAL.Models.UserSkill

import scala.concurrent.Future

trait IUserSkillRepository {
  def deleteByUserId(id: Long): Future[Int]
  def deleteBySkillId(id: Long): Future[Int]
  def deleteByUserAndSkillId(userId: Long, skillId: Long): Future[Int]
  def getByUserAndSkillId(userId: Long, skillId: Long): Future[Option[UserSkill]]
  def getByUserId(userId: Long): Future[Seq[UserSkill]]
  def getBySkillId(skillId: Long): Future[Seq[UserSkill]]
  def create(userSkill: UserSkill): Future[Int]
  def createMultiple(skills: Seq[UserSkill]): Future[Int]
}
