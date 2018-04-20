package BLL.Services

import BLL.Converters
import javax.inject._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import BLL.Models.{OperationResult, _}
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

  def create(user: UserModel): OperationResult[Long] = {
    user.password.fold(OperationResult[Long](isSuccess = false,
      "No user password provided", 0L))(password => {
      val hashedPassword = authService.hashPassword(password)
      val dbUser = Converters.userModelToUser(user, hashedPassword)

      val addUserF = userRepository.create(dbUser)
      val addUserResultId = Await.result(addUserF,  timeoutDuration)
      if(addUserResultId.getOrElse(0L) > 0L) {
        val dbUserDetail = UserDetail(0L, description = "", country = "", religion = "", height = 0.0,
          weight= 0.0, skin = "", hair = "", gender = "", age = 0, userId = addUserResultId.get)
        val addUserDetailF = userDetailRepository.create(dbUserDetail)
        val addUserDetailId = Await.result(addUserDetailF,  timeoutDuration).getOrElse(0L)
        if (addUserDetailId > 0L) OperationResult[Long](isSuccess = true, "User created", addUserDetailId)
        else OperationResult[Long](isSuccess = false, "User not created", 0L)
      }
      else OperationResult[Long](isSuccess = false, "User not created", 0L)
    })
  }

  def addSkills(userId: Long, skills: Seq[Long]): OperationResult[Long] = {
    val oUser = Await.result(userRepository.getById(userId), timeoutDuration)

    oUser match {
      case Some(user) =>
        val addUserSkill = Await.result(userRepository.addSkills(user, skills), timeoutDuration)
        addUserSkill match {
          case Some(0L) | None => OperationResult[Long](isSuccess = false, "No user skills added", 0L)
          case Some(skillCount) => OperationResult[Long](isSuccess = true, "User skills added", skillCount)
        }
      case None => OperationResult[Long](isSuccess = false, "No user with specified id", 0L)
    }
  }

  def addLeaves(userId: Long, skills: Seq[Long]): OperationResult[Long] = {
    val oUser = Await.result(userRepository.getById(userId), timeoutDuration)

    oUser match {
      case Some(user) =>
        val addUserSkill = Await.result(userRepository.addLeaves(user, skills), timeoutDuration)
        addUserSkill match {
          case Some(0L) | None => OperationResult[Long](isSuccess = false, "No user leaves added", 0L)
          case Some(leavesCount) => OperationResult[Long](isSuccess = true, "User leaves added", leavesCount)
        }
      case None => OperationResult[Long](isSuccess = false,  "No user with specified id", 0L)
    }
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
