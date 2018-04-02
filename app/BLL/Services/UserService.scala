package BLL.Services

import javax.inject._

import play.api.libs.json.Json
import play.api.data.Form
import play.api.data.Forms._

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

import BLL.Models._

import DAL.Repository._
import DAL.Models._
import DAL.Traits._

object UserForm {
  val form = Form(
    mapping(
      "id" -> longNumber,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "mobile" -> longNumber,
      "email" -> email,
      "password" -> optional(text)
    )(UserModel.apply)(UserModel.unapply)
  )
}

class UserService @Inject()(ussr: IUserRepository, authService: AuthenticationService) {
  private val userRepository: IUserRepository = ussr
  
  def add(user: User): Future[String] = {
    val hashedPassword = authService.hashPassword(user.password)
    val dbUser = User(0L, user.firstName, user.lastName, user.mobile, user.email, hashedPassword)
    userRepository.add(dbUser)
  }

  def delete(id: Long): Future[Int] = {
    userRepository.delete(id)
  }

  def getById(id: Long): Option[UserModel] = {
    val opUser = Await.result(userRepository.getById(id),  3 seconds)

    opUser match {
      case Some(dbUser) => {
        val user:Option[UserModel] = Some(UserModel(dbUser.id, dbUser.firstName, dbUser.lastName, dbUser.mobile, dbUser.email, None))
        user
      }
      case None => None
    }

  }

  def get: Seq[UserModel] = {
    val dbUsers = Await.result(userRepository.get,  3 seconds)

    val users = dbUsers.map(sUser => UserModel(sUser.id, sUser.firstName, sUser.lastName, sUser.mobile, sUser.email, None)).toSeq

    users
  }
}
