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
  val usersDetails = TableQuery[UserDetailTable]

  def create(user: UserDetail): Future[OperationResult[UserDetail]] = {
    runCommand(usersDetails += user)
      .map(_ =>
        OperationResult[UserDetail](isSuccess = true, "User detail successfully added", operationObject = Some(user)))
      .recover {
        case ex: Exception => OperationResult[UserDetail](isSuccess = false, ex.getMessage, operationObject = None)
    }
  }

  def delete(id: Long): Future[Int] = {
    runCommand(usersDetails.filter(_.id === id).delete)
  }

  def update(user: UserDetail) : Future[OperationResult[UserDetail]] = {
    val mapUpdateAction = usersDetails.filter(_.id === user.id)
      .map(dbUser => (dbUser.gender, dbUser.skin, dbUser.weight, dbUser.height, dbUser.hair,
        dbUser.description, dbUser.religion, dbUser.age, dbUser.country))
      .update( (user.gender, user.skin, user.weight, user.height, user.hair, user.description,
        user.religion, user.age, user.country))

    runCommand(mapUpdateAction)
      .map(updateCount => {
        if (updateCount <= 0) {
          OperationResult(isSuccess = false, "User details not updated", operationObject = Some(user))
        } else {
          OperationResult(isSuccess = true, "User details successfully updated", operationObject = Some(user))
        }
      })
      .recover {
        case ex : Exception => OperationResult(isSuccess = false, ex.getMessage, operationObject = None)
    }
  }

  def getById(id: Long): Future[Option[UserDetail]] = {
    runCommand(usersDetails.filter(_.id === id).result).map(_.headOption)
  }

  def getByUserId(userId: Long): Future[Option[UserDetail]] = {
    runCommand(usersDetails.filter(_.userId === userId).result).map(_.headOption)
  }

  def getByUserIds(userIds: Seq[Long]): Future[Seq[UserDetail]] = {
    runCommand(usersDetails.filter(detail => detail.userId inSet userIds.seq).result).map(_.seq)
  }

  def get: Future[Seq[UserDetail]] = {
    runCommand(usersDetails.result)
  }
}

