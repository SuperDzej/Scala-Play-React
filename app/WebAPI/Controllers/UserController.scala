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

  def getWitLimitAndOffset(offset: Long, limit: Long) = Action {
    val users = userService.getWithOffsetAndLimit(offset, limit)
    Ok(Json.toJson(new RestResponse(Json.toJson(users), None).toJson))
  }

  def getById(id: Long) = Action {
    val user: Option[UserModel] = userService.getById(id)
    user match {
      case userM: Some[UserModel] => Ok(Json.toJson(userM))
      case None => NotFound
    }
  }

  def total = Action {
    Ok(userService.getTotal.toString)
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

  def updateDetails(id: Long): Action[JsValue] = Action(parse.json) { request =>
    val userDetailFromBody = request.body.as[UserDetailModel]

    val updateResult:OperationResult[Option[UserDetailModel]] =
      userService.updateDetails(id, userDetailFromBody)

    if(updateResult.isSuccess) Ok(Json.toJson(updateResult.result))
    else BadRequest(updateResult.message)
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

      if(addLeavesResult.isSuccess) Ok(Json.toJson(addLeavesResult.result))
      else BadRequest(addLeavesResult.message)
    } else {
      BadRequest("Invalid data sent")
    }
  }

  def addSkills(id: Long): Action[JsValue] = Action(parse.json) { request =>
    val userSkillsBodyValidation = request.body.validate[Seq[UserSkillModel]]
    if(userSkillsBodyValidation.isSuccess) {
      val skills = request.body.as[Seq[UserSkillModel]]
      val addSkillsResult  = userService.addSkills(id, skills)

      if(addSkillsResult.isSuccess) Ok(Json.toJson(addSkillsResult.result))
      else BadRequest(addSkillsResult.message)
    } else {
      BadRequest("Invalid data sent")
    }
  }

  def addProjects(id: Long): Action[JsValue] = Action(parse.json) { request =>
    val userProjectsBodyValidation = request.body.validate[Seq[Long]]
    if(userProjectsBodyValidation.isSuccess) {
      val projects = request.body.as[Seq[Long]]
      val addProjectsResult  = userService.addProjects(id, projects)

      if(addProjectsResult.isSuccess) Ok(Json.toJson(addProjectsResult.result))
      else BadRequest(addProjectsResult.message)
    } else {
      BadRequest("Invalid data sent")
    }
  }


}