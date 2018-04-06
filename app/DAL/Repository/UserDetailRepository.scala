package DAL.Repository

import DAL.Helpers.OperationResult

import scala.concurrent.Future
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import javax.inject._
import DAL.Models.UserDetail
import DAL.Traits._
import DAL.Migrations.UserDetailTable

class UserDetailRepository @Inject()() extends BaseRepository() with IUserDetailRepository {
  val users = TableQuery[UserDetailTable]

  def create(user: UserDetail): Future[OperationResult[UserDetail]] = {
    runCommand(users += user)
      .map(_ => OperationResult[UserDetail](isSuccess = true, "User detail successfully added", operationObject = Some(user)))
      .recover {
        case ex: Exception => OperationResult[UserDetail](isSuccess = false, ex.getMessage, operationObject = None)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(users.filter(_.id === id).delete)
  }

  def update(user: UserDetail) : Future[OperationResult[UserDetail]] = {
    runCommand(users.update(user))
      .map(_ => OperationResult(isSuccess = false, "User detail successfully updated", operationObject = Some(user)))
      .recover {
        case ex : Exception => OperationResult(isSuccess = false, ex.getMessage, operationObject = None)
    }
  }

  def getById(id: Long): Future[Option[UserDetail]] = {
    runCommand(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[UserDetail]] = {
    runCommand(users.result)
  }
}

