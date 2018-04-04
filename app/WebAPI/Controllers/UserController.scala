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

  def get() = Action {
    val users: Seq[UserModel] = userService.get
    Ok(Json.toJson(users))
  }

  def getById(userId: Long) = Action { _ =>
    val user: Option[UserModel] = userService.getById(userId)
    val userJson: JsValue = Json.toJson(user)
    Ok(userJson)   
  }

  def post : Action[JsValue] = Action(parse.json) { request =>
    val jsonUserFromBody = request.body.as[User]

    val addResult: Future[String] = userService.add(jsonUserFromBody)
    val result = Await.result(addResult,  3.seconds)
    Ok(Json.toJson(result))
  }

  def delete(userId: Long) = Action { request =>
    val deletedUserF: Future[Int] = userService.delete(userId)
    val deletedUserId = Await.result(deletedUserF,  3.seconds)
    if(deletedUserId == userId) {
      Ok("User deleted")
    } else {
      NotFound("No user with id")
    }
  }
}
