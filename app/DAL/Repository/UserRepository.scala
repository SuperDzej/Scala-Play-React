package DAL.Repository

import play.api.Play
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import slick.jdbc.PostgresProfile.api._ // Important because sql queries are transpiled wrong if used some other profile
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject._

import DAL.Models.User
import DAL.Traits._
import DAL.Migrations.UserTableDef

import BLL.Models.UserModel

object UserForm {

  val form = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "mobile" -> longNumber,
      "email" -> email
    )(UserModel.apply)(UserModel.unapply)
  )
}

class UserRepository @Inject()() extends BaseRepository() with IUserRepository {
  val users = TableQuery[UserTableDef]

  def add(user: User): Future[String] = {
    getDatabaseConnection().run(users += user).map(res => "User successfully added").recover {
      case ex: Exception => ex.getCause.getMessage
    }
  }

  def delete(id: Long): Future[Int] = {
    getDatabaseConnection().run(users.filter(_.id === id).delete)
  }

  def getById(id: Long): Future[Option[User]] = {
    getDatabaseConnection().run(users.filter(_.id === id).result).map(_.headOption)
  }

  def get: Future[Seq[User]] = {
    getDatabaseConnection().run(users.result)
  }
}

