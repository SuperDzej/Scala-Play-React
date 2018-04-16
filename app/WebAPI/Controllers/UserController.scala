package WebApi.Controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Models._
import BLL.Services._
import DAL.Repository.UserDetailRepository

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService,
                               userDetailRepository: UserDetailRepository)
  extends AbstractController(cc) {

  def getWitLimitAndOffset(offset: Long, limit: Long) = Action { _ =>
    val users = userService.getWithOffsetAndLimit(offset, limit)
    Ok(Json.toJson(users))
  }

  def getById(userId: Long) = Action { _ =>
    val user: Option[UserModel] = userService.getById(userId)
    user match {
      case userM: Some[UserModel] => Ok(Json.toJson(userM))
      case None => NotFound
    }
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val jsonUserFromBody = request.body.as[UserModel]

    val addResult: String = userService.create(jsonUserFromBody)
    Ok(Json.toJson(addResult))
  }

  def updateDetails(userId: Long): Action[JsValue] = Action(parse.json) { request =>
    val jsonUserDetailFromBody = request.body.as[UserDetailModel]
    println(jsonUserDetailFromBody)

    val addResult = userService.updateDetails(userId, jsonUserDetailFromBody)
    Ok(Json.toJson(addResult))
  }

  def delete(userId: Long) = Action {
    val deletedUserId: Int = userService.delete(userId)
    if(deletedUserId == userId) {
      Ok("User deleted")
    } else {
      NotFound("No user with id for deletion")
    }
  }
}