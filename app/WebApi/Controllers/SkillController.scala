package WebApi.Controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Models._
import BLL.Services._
import DAL.Repository.SkillRepository

@Singleton
class SkillController @Inject()(cc: ControllerComponents, skillService: SkillService,
                                skillRepository: SkillRepository)
  extends AbstractController(cc) {

  def getById(id: Long) = Action {
    val user: Option[SkillModel] = skillService.getById(id)
    user match {
      case userM: Some[SkillModel] => Ok(Json.toJson(userM))
      case None => NotFound("No user with id")
    }
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val jsonUserFromBody = request.body.as[SkillModel]

    val addResult: String = skillService.create(jsonUserFromBody)
    Ok(Json.toJson(addResult))
  }

  def delete(userId: Long) = Action {
    val deletedUserId: Int = skillService.delete(userId)
    if(deletedUserId == userId) {
      Ok("User deleted")
    } else {
      NotFound("No user with id for deletion")
    }
  }
}