package BLL.Services

import javax.inject._

import play.api.libs.json.Json

import scala.concurrent._
import scala.concurrent.duration._

import scala.concurrent.{Await, Future}

import BLL.Models._

import DAL.Repository._
import DAL.Models._
import DAL.Traits._

class UserService @Inject()(ussr: IUserRepository) {
  private val userRepository: IUserRepository = ussr

  def add(user: UserModel): Future[String] = {
    val dbUser = User(0L, user.firstName, user.lastName, user.mobile, user.email)
    userRepository.add(dbUser)
  }

  def delete(id: Long): Future[Int] = {
    userRepository.delete(id)
  }

  def getById(id: Long): Option[UserModel] = {
    val opUser = Await.result(userRepository.getById(id),  3 seconds)

    opUser match {
      case Some(dbUser) => {
        val user:Option[UserModel] = Some(UserModel(dbUser.firstName, dbUser.lastName, dbUser.mobile, dbUser.email))
        user
      }
      case None => None
    }

  }

  def get: Seq[UserModel] = {
    val dbUsers = Await.result(userRepository.get,  3 seconds)

    val users = dbUsers.map(sUser => UserModel(sUser.firstName, sUser.lastName, sUser.mobile, sUser.email)).toSeq

    users
  }
}
