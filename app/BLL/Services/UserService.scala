package BLL.Services

import BLL.Converters
import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import BLL.Models._
import DAL.Models._
import DAL.Traits._

/*object UserForm {
  val form = Form(
    mapping(
      "id" -> longNumber,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> email,
      "username" -> nonEmptyText,
      "password" -> optional(text),
      "userDetails" -> optional[UserDetail](s)
    )(UserModel.apply)(UserModel.unapply)
  )
}*/

class UserService @Inject()(private val userRepository: IUserRepository,
                            private val userDetailRepository: IUserDetailRepository,
                            private val authService: AuthenticationService) {
  private val timeoutDuration = 2.seconds

  def create(user: UserModel): String = {
    user.password.fold("No user password provided")(password => {
      val hashedPassword = authService.hashPassword(password)
      val dbUser = Converters.userModelToUser(user, hashedPassword)

      val addUserF = userRepository.create(dbUser)
      val addUserResultId = Await.result(addUserF,  timeoutDuration)
      if(addUserResultId.getOrElse(0L) > 0L) {
        val dbUserDetail = UserDetail(0L, description = "", country = "", religion = "", height = 0.0,
          weight= 0.0, skin = "", hair = "", gender = "", age = 0, userId = addUserResultId.get)
        val addUserDetailF = userDetailRepository.create(dbUserDetail)
        val addUserDetailResult = Await.result(addUserDetailF,  timeoutDuration)
        if (addUserDetailResult.getOrElse(0L) > 0L) {
          "User created"
        } else {
          "User not created"
        }
      } else {
        "User not created"
      }
    })
  }

  def updateDetails(userId: Long, userDetail: UserDetailModel): String = {
    userDetail.userId = Some(userId)
    val dbUserDetail = Converters.userDetailModelToUserDetail(userDetail)

    val addDetailsF = userDetailRepository.update(dbUserDetail)
    val updateUserResult = Await.result(addDetailsF,  timeoutDuration)
    updateUserResult match {
      case Some(_) => "User updated"
      case None => "User not updated"
    }
  }

  def delete(id: Long): Int = {
    val deleteUserF: Future[Int] = userRepository.delete(id)
    Await.result(deleteUserF,  timeoutDuration)
  }

  def getById(id: Long): Option[UserModel] = {
    val opUser: Option[User] = Await.result(userRepository.getById(id),  timeoutDuration)

    opUser match {
      case Some(dbUser) =>
        val opUserDetail = Await.result(userDetailRepository.getByUserId(dbUser.id), timeoutDuration)
        opUserDetail match {
          case Some(uDetail) =>
            val userDetail = Some(Converters.userDetailToUserDetailModel(uDetail, None))
            Some(Converters.userToUserModel(dbUser, userDetail))
          case None => None
        }
      case None => None
    }
  }

  def getWithOffsetAndLimit(offset: Long, limit: Long): Seq[UserModel] = {
    if (limit > 1000) {
      return Seq.empty[UserModel]
    }

    val dbUsers = Await.result(userDetailRepository.getWithOffsetAndLimit(offset, limit), timeoutDuration)
    dbUsers.map(userWithDetail => {
      val userDetailM = Some(Converters.userDetailToUserDetailModel(userWithDetail._2, None))
      Converters.userToUserModel(userWithDetail._1, userDetailM)
    })
  }
}
