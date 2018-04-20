package DAL.Traits

import DAL.Models._
import DAL.Repository._
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[UserRepository])
trait IUserRepository {
  def create(user: User): Future[Option[Long]]

  def addSkills(user: User, skills: Seq[Long]): Future[Option[Long]]

  def addLeaves(user: User, leaves: Seq[Long]): Future[Option[Long]]

  def delete(id: Long): Future[Int]

  def update(user: User) : Future[Option[User]]

  def getById(id: Long): Future[Option[User]]

  def getByEmail(email: String): Future[Option[User]]

  def getWithOffsetAndLimit(offset: Long, limit: Long): Future[Seq[User]]

  def get: Future[Seq[User]]
}
