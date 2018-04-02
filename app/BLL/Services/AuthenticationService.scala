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

import org.mindrot.jbcrypt.BCrypt

class AuthenticationService @Inject()(ussr: IUserRepository) {
  private val userRepository: IUserRepository = ussr

  def validatePassword(password: String, passwordHash: String): Boolean = {
    BCrypt.checkpw(password, passwordHash)
  }

  def hashPassword(password: String): String = {
    BCrypt.hashpw(password, BCrypt.gensalt())
  }

}
