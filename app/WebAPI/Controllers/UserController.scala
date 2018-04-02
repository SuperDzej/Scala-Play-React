package controllers

import javax.inject._
import java.io.File
import java.io.FileInputStream

import play.api.libs.json._
import play.api.mvc._

import akka.actor._
import akka.stream.scaladsl.{Source, StreamConverters, Flow , Sink}

import scala.concurrent._
import scala.concurrent.duration._

import DAL.Models._

import BLL.Models._
import BLL.Services._

import DAL.Repository._

@Singleton
class UserController @Inject()(cc: ControllerComponents, ussc: UserService) 
  extends AbstractController(cc) {

  private val userService: UserService = ussc
  private implicit val objectWrites = Json.writes[UserModel]
  private implicit val objectReads = Json.reads[UserModel]
  private implicit val userReads = Json.reads[User]
  // private implicit val reads = Json.format[UserModel]

  def get() = Action{ request => 
    val users: Seq[UserModel] = userService.get
    Ok(Json.toJson(users))
  }

  def getById(userId: Long) = Action { request => 
    val user: Option[UserModel] = userService.getById(userId)
    val userJson: JsValue = Json.toJson(user)
    Ok(userJson)   
  }

  def post = Action(parse.json) { request => 
    val jsonUserFromBody = request.body.as[User]

    val addResult: Future[String] = userService.add(jsonUserFromBody)
    val result = Await.result(addResult,  3 seconds)
    Ok(Json.toJson(result))
  }

  def delete(userId: Long) = Action { request =>
    val deletedUserF: Future[Int] = userService.delete(userId)
    val deletedUserId = Await.result(deletedUserF,  3 seconds)
    if(deletedUserId == userId) {
      Ok("User deleted")
    } else {
      NotFound("No user with id")
    }
  }
}
