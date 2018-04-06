package BLL.Services

import javax.inject._
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent.duration._
import scala.concurrent.Await
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

class UserService @Inject()(ussr: IUserRepository, iusdr: IUserDetailRepository,
                            authService: AuthenticationService) {
  private val userRepository: IUserRepository = ussr
  private val userDetailRepository: IUserDetailRepository = iusdr
  private val timeoutDuration = 2.seconds

  def create(user: UserModel): String = {
    user.password.fold("No user password provided")(password => {
      val hashedPassword = authService.hashPassword(password)
      val dbUser = User(0L, user.firstName, user.lastName, user.email, user.username,
        isVerified = false, isDisabled = false, hashedPassword)

      val addUserF = userRepository.create(dbUser)
      val addUserResult = Await.result(addUserF,  timeoutDuration)
      if(addUserResult.isSuccess) {
        val dbUserDetail = UserDetail(0L, description = "", country = "", religion = "", height = 0.0,
          weight= 0.0, skin = "", hair = "", gender = "", age = 0, userId = addUserResult.operationObject.get.asInstanceOf[UserDetail].id)
        val addUserDetailF = userDetailRepository.create(dbUserDetail)
        val addUserDetailResult = Await.result(addUserDetailF,  timeoutDuration)
        addUserDetailResult.message
      } else {
        addUserResult.message
      }
    })
  }

  def updateDetails(userId: Long, userDetail: UserDetailModel): String = {
    val dbUserDetail = UserDetail(userDetail.id, description= userDetail.description, country = userDetail.country,
      religion = userDetail.religion, height = userDetail.height, weight = userDetail.weight, skin = userDetail.skin,
      hair = userDetail.hair, gender = userDetail.gender, age = userDetail.age, userId = userId)

    val addDetailsF = userDetailRepository.update(dbUserDetail)
    val addUserResult = Await.result(addDetailsF,  timeoutDuration)
    addUserResult.message
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
        val user:Option[UserModel] = Some(UserModel(Some(dbUser.id), dbUser.firstName, dbUser.lastName,
          dbUser.email, dbUser.username, None, None))
        user
      case None => None
    }
  }

  def get: Seq[UserModel] = {
    val dbUsers = Await.result(userRepository.get,  timeoutDuration)

    val users = dbUsers.map(user => UserModel(Some(user.id), user.firstName, user.lastName,
      user.email, user.username, None, None))
    users
  }
}
