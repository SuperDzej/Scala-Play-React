package WebApi.Controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Models._
import BLL.Services._
import DAL.Repository.UserDetailRepository
import WebApi.Models.RestResponse

@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               userService: UserService,
                               userDetailRepository: UserDetailRepository)
  extends AbstractController(cc) {

  def getWitLimitAndOffset(offset: Long, limit: Long) = Action { _ =>
    val users = userService.getWithOffsetAndLimit(offset, limit)
    Ok(Json.toJson(new RestResponse(Json.toJson(users), None).toJson))
  }

  def getById(id: Long) = Action { _ =>
    val user: Option[UserModel] = userService.getById(id)
    user match {
      case userM: Some[UserModel] => Ok(Json.toJson(userM))
      case None => NotFound
    }
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val userBodyValidation = request.body.validate[UserModel]
    if(userBodyValidation.isSuccess) {
      val userModel = request.body.as[UserModel]
      Created(Json.toJson(userService.create(userModel)))
    } else {
      BadRequest("Invalid data sent")
    }
  }

  def updateDetails(userId: Long): Action[JsValue] = Action(parse.json) { request =>
    val jsonUserDetailFromBody = request.body.as[UserDetailModel]

    val updateResult:String = userService.updateDetails(userId, jsonUserDetailFromBody)
    Ok(updateResult)
  }

  def delete(id: Long) = Action {
    val deletedUserId: Int = userService.delete(id)
    if(deletedUserId == id) {
      Ok("User deleted")
    } else {
      NotFound("No user with id for deletion")
    }
  }

  def addVacations(id: Long) = Action {
    NotFound
  }
}