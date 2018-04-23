package DAL.Repository

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models._
import DAL.Traits._

class UserProjectRepository @Inject()() extends BaseRepository() with IUserProjectRepository {
  def deleteByUserId(id: Long): Future[Int] = {
    runCommand(userProjects.filter(_.userId === id).delete)
  }

  def deleteByProjectId(id: Long): Future[Int] = {
    runCommand(userProjects.filter(_.projectId === id).delete)
  }

  def deleteByUserAndProjectId(userId: Long, projectId: Long): Future[Int] = {
    runCommand(userProjects.filter(userProject => userProject.projectId === projectId &&
      userProject.userId === userId).delete)
  }

  def getByUserAndProjectId(userId: Long, projectId: Long): Future[Option[UserProject]] = {
    runCommand(userProjects.filter(userProject => userProject.userId === userId &&
      userProject.projectId === projectId).result).map(_.headOption)
  }

  def getByUserId(userId: Long): Future[Seq[UserProject]] = {
    runCommand(userProjects.filter(_.userId === userId).result)
  }

  def getByProjectId(projectId: Long): Future[Seq[UserProject]] = {
    runCommand(userProjects.filter(_.projectId === projectId).result)
  }

  def create(userProject: UserProject): Future[Int] = {
    val query = for {
      addCount <- userProjects += userProject
    } yield addCount
    runCommand(query)
  }

  def createMultiple(userProjects: Seq[UserProject]): Future[Int] = {
    val query = for {
      addCount <- this.userProjects ++= userProjects
    } yield addCount

    runCommand(query).map(addedCount => {
      addedCount.getOrElse(0)
    }).recover {
      case e: Exception => println("Ex: " + e.getLocalizedMessage); 0
    }
  }
}
