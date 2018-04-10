package DAL.Traits

import DAL.Helpers.OperationResult
import DAL.Models._
import DAL.Repository._
import com.google.inject.ImplementedBy

import scala.concurrent.Future

@ImplementedBy(classOf[UserRepository])
trait IUserRepository {
  def create(user: User): Future[OperationResult[User]]

  def delete(id: Long): Future[Int]

  def update(user: User) : Future[OperationResult[User]]

  def getById(id: Long): Future[Option[User]]

  def getByEmail(email: String): Future[Option[User]]

  def getWithDetailsTakeOffsetAndLimit(offset: Long, limit: Long): Future[Seq[(User, UserDetail)]]

  def getWithOffsetAndLimit(offset: Long, limit: Long): Future[Seq[User]]

  def getWithDetails: Future[Seq[(User, UserDetail)]]

  def get: Future[Seq[User]]
}
