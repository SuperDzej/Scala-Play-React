package DAL.Traits

import DAL.Models.UserProject

import scala.concurrent.Future

trait IUserProjectRepository {
  def deleteByUserId(id: Long): Future[Int]
  def deleteByProjectId(id: Long): Future[Int]
  def deleteByUserAndProjectId(userId: Long, projectId: Long): Future[Int]
  def getByUserAndProjectId(userId: Long, projectId: Long): Future[Option[UserProject]]
  def getByUserId(userId: Long): Future[Seq[UserProject]]
  def getByProjectId(projectId: Long): Future[Seq[UserProject]]
  def create(userProject: UserProject): Future[Int]
  def createMultiple(skills: Seq[UserProject]): Future[Int]
}
