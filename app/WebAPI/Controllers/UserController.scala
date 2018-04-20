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
      val createResult = userService.create(userModel)
      if(createResult.isSuccess) Created(Json.toJson(createResult.result))
      else BadRequest(createResult.message)
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

  def addLeaves(id: Long): Action[JsValue] = Action(parse.json) { request =>
    val userLeavesBodyValidation = request.body.validate[Seq[Long]]
    if(userLeavesBodyValidation.isSuccess) {
      val leaves = request.body.as[Seq[Long]]
      val addLeavesResult = userService.addLeaves(id, leaves)

      if(addLeavesResult.isSuccess) Created(Json.toJson(addLeavesResult.result))
      else BadRequest(addLeavesResult.message)
    } else {
      NotFound(userLeavesBodyValidation.isSuccess.toString)
    }
  }

  def addSkills(id: Long): Action[JsValue] = Action(parse.json) { request =>
    val userSkillsBodyValidation = request.body.validate[Seq[Long]]
    if(userSkillsBodyValidation.isSuccess) {
      val skills = request.body.as[Seq[Long]]
      val addSkillsResult  = userService.addSkills(id, skills)

      if(addSkillsResult.isSuccess) Created(Json.toJson(addSkillsResult.result))
      else BadRequest(addSkillsResult.message)
    } else {
      NotFound(userSkillsBodyValidation.isSuccess.toString)
    }
  }
}