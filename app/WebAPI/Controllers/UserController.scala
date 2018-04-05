package WebApi.Controllers

import javax.inject._

import play.api.libs.json._
import play.api.mvc._

import scala.concurrent._
import scala.concurrent.duration._

import DAL.Models._

import BLL.Models._
import BLL.Services._

@Singleton
class UserController @Inject()(cc: ControllerComponents, ussc: UserService) 
  extends AbstractController(cc) {

  private val userService: UserService = ussc
  private implicit val objectWrites: Writes[UserModel] = Json.writes[UserModel]
  private implicit val objectReads: Reads[UserModel] = Json.reads[UserModel]
  private implicit val userReads: Reads[User] = Json.reads[User]
  private implicit val userDetailReads: Reads[UserDetail] = Json.reads[UserDetail]

  def get = Action {
    val users: Seq[UserModel] = userService.get
    Ok(Json.toJson(users))
  }

  def getById(userId: Long) = Action { _ =>
    val user: Option[UserModel] = userService.getById(userId)
    val userJson: JsValue = Json.toJson(user)
    Ok(userJson)
  }

  def post : Action[JsValue] = Action(parse.json) { request =>
    val jsonUserFromBody = request.body.as[UserModel]

    val addResult: String = userService.create(jsonUserFromBody)
    Ok(Json.toJson(addResult))
  }

  def postDetails(userId: Long) : Action[JsValue] = Action(parse.json) { request =>
    val jsonUserDetailFromBody = request.body.as[UserModel]

    println(jsonUserDetailFromBody)
    Ok(Json.toJson("se" + userId))
  }

  def delete(userId: Long) = Action {
    val deletedUserId: Int = userService.delete(userId)
    if(deletedUserId == userId) {
      Ok("User deleted")
    } else {
      NotFound("No user with id")
    }
  }
}
