package DAL.Traits

import DAL.Models._
import DAL.Repository._

import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[UserRepository])
trait IUserRepository {
  def add(user: User): Future[String]

  def delete(id: Long): Future[Int]

  def getById(id: Long): Future[Option[User]]

  def getByEmail(email: String): Future[Option[User]]

  def get: Future[Seq[User]]
}
