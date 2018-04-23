package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.Traits._

class UserSkillRepository @Inject()() extends BaseRepository() with IUserSkillRepository{
  def deleteByUserId(id: Long): Future[Int] = {
    runCommand(userSkills.filter(_.userId === id).delete)
  }

  def deleteBySkillId(id: Long): Future[Int] = {
    runCommand(userSkills.filter(_.skillId === id).delete)
  }

  def deleteByUserAndSkillId(userId: Long, skillId: Long): Future[Int] = {
    runCommand(userSkills.filter(userSkill => userSkill.skillId === skillId &&
      userSkill.userId === userId).delete)
  }

  def getByUserAndSkillId(userId: Long, skillId: Long): Future[Option[UserSkill]] = {
    runCommand(userSkills.filter(userSkill => userSkill.userId === userId &&
      userSkill.skillId === skillId).result).map(_.headOption)
  }

  def getByUserId(userId: Long): Future[Seq[UserSkill]] = {
    runCommand(userSkills.filter(_.userId === userId).result)
  }

  def getBySkillId(skillId: Long): Future[Seq[UserSkill]] = {
    runCommand(userSkills.filter(_.skillId === skillId).result)
  }

  def create(userSkill: UserSkill): Future[Int] = {
    val query = for {
      addCount <- userSkills += userSkill
    } yield addCount

    runCommand(query)
  }

  def createMultiple(userSkills: Seq[UserSkill]): Future[Int] = {
    val query = for {
      addCount <- this.userSkills ++= userSkills
    } yield addCount

    runCommand(query).map(addedCount => {
      addedCount.getOrElse(0)
    }).recover {
      case e: Exception => println("Ex: " + e.getLocalizedMessage); 0
    }
  }
}
