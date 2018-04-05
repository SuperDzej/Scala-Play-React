package BLL.Services

import javax.inject._
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.duration._
import scala.concurrent.Await
import BLL.Models._
import DAL.Migrations.{False, True}
import DAL.Models._
import DAL.Traits._

object UserForm {
  val form = Form(
    mapping(
      "id" -> longNumber,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> email,
      "username" -> nonEmptyText,
      "password" -> optional(text)
    )(UserModel.apply)(UserModel.unapply)
  )
}

class UserService @Inject()(ussr: IUserRepository, authService: AuthenticationService) {
  private val userRepository: IUserRepository = ussr
  private val timeoutDuration = 2.seconds

  def create(user: UserModel): String = {
    user.password.fold("No user password provided")(password => {
      val hashedPassword = authService.hashPassword(password)
      val dbUser = User(0L, user.firstName, user.lastName, user.email, user.username,
        isVerified = 0, isDisabled = 0, hashedPassword)

      val addUserF = userRepository.create(dbUser)
      val addUserResult = Await.result(addUserF,  timeoutDuration)
      addUserResult.message
    })
  }

  def delete(id: Long): Int = {
    val deleteUserF = userRepository.delete(id)
    val deleteUserId = Await.result(deleteUserF,  timeoutDuration)
    deleteUserId
  }

  def getById(id: Long): Option[UserModel] = {
    val opUser = Await.result(userRepository.getById(id),  timeoutDuration)

    opUser match {
      case Some(dbUser) =>
        val user:Option[UserModel] = Some(UserModel(dbUser.id, dbUser.firstName, dbUser.lastName,
          dbUser.email, dbUser.username, None))
        user
      case None => None
    }
  }

  def get: Seq[UserModel] = {
    val dbUsers = Await.result(userRepository.get,  timeoutDuration)

    val users = dbUsers.map(user => UserModel(user.id, user.firstName, user.lastName,
      user.email, user.username, None))
    users
  }
}
