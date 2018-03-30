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
  private implicit val reads = Json.format[UserModel]

  def get() = Action{ request => 
    val users: Seq[UserModel] = userService.get
    Ok(Json.toJson(users))
  }

  def getWithQuery(userId: Long) = Action { request => 
    val user: Option[UserModel] = userService.getById(userId)
    val jsonResult: JsValue = Json.toJson(user)
    Ok(jsonResult)   
  }

  def post = Action(parse.json) { request => 
    val json = request.body.as[UserModel]

    val se: Future[String] = userService.add(json)
    val result = Await.result(se,  3 seconds)
    Ok(Json.toJson(result))
  }

  def delete(userId: Long) = Action { request =>
    val se: Future[Int] = userService.delete(userId)
    val result = Await.result(se,  3 seconds)
    if(result == userId) {
      Ok("User deleted")
    } else {
      NotFound("No user with id")
    }
  }
}
