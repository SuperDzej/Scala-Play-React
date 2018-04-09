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
          weight= 0.0, skin = "", hair = "", gender = "", age = 0, userId = addUserResult.operationObject.get.id)
        val addUserDetailF = userDetailRepository.create(dbUserDetail)
        val addUserDetailResult = Await.result(addUserDetailF,  timeoutDuration)
        if (addUserDetailResult.isSuccess) {
          addUserResult.message
        } else {
          addUserResult.message
        }
      } else {
        addUserResult.message
      }
    })
  }

  def updateDetails(userId: Long, userDetail: UserDetailModel): String = {
    val dbUserDetail = UserDetail(userDetail.id.getOrElse(0L), description= userDetail.description, country = userDetail.country,
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
        val opUserDetail = Await.result(userDetailRepository.getByUserId(dbUser.id), timeoutDuration)
        opUserDetail match {
          case Some(uDetail) =>
            val userDetail = Some(UserDetailModel(id = Some(uDetail.id), description = uDetail.description, country = uDetail.country,
              religion = uDetail.religion, height = uDetail.height, weight = uDetail.weight, skin = uDetail.skin,
              hair = uDetail.hair, gender = uDetail.gender, age = uDetail.age, userId = uDetail.userId))
            val user: Option[UserModel] = Some(UserModel(Some(dbUser.id), dbUser.firstName, dbUser.lastName,
              dbUser.email, dbUser.username, None, userDetail))
            user
          case None => None
        }
      case None => None
    }
  }

  def get: Seq[UserModel] = {
    val dbUsers = Await.result(userRepository.getWithDetails,  timeoutDuration)

    val users = dbUsers.map(userWithDetail => {
      val userDetailM = Some(UserDetailModel(Some(userWithDetail._2.id), description = userWithDetail._2.description, country = userWithDetail._2.country,
        religion = userWithDetail._2.religion, skin = userWithDetail._2.skin, hair = userWithDetail._2.hair, height = userWithDetail._2.height,
        weight = userWithDetail._2.weight, gender = userWithDetail._2.gender, age = userWithDetail._2.age, userId = userWithDetail._2.userId))

      UserModel(Some(userWithDetail._1.id), userWithDetail._1.firstName, userWithDetail._1.lastName,
        userWithDetail._1.email, userWithDetail._1.username, None, userDetailM)
    } )
    users
  }
}
