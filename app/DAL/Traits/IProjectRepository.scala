package DAL.Traits

import DAL.Models.{Project, Skill}

import scala.collection.mutable
import scala.concurrent.Future

trait IProjectRepository {
  def create(project: Project): Future[Option[Long]]

  def addSkills(project: Project, skills: Seq[Long]): Future[Option[Long]]

  def delete(id: Long): Future[Int]

  def update(project: Project) : Future[Option[Project]]

  def getById(id: Long): Future[Option[Project]]

  def get: Future[Seq[Project]]

  def getWithSkills: Future[Seq[(Project, mutable.ArrayBuffer[Skill])]]
}
