package WebApi.Controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Models._
import BLL.Services._
import DAL.Repository.VacationRepository

@Singleton
class VacationController @Inject()(cc: ControllerComponents, vacationService: VacationService,
                                   vacationRepository: VacationRepository)
  extends AbstractController(cc) {

  def getById(id: Long) = Action {
    /*val user: Option[SkillModel] = vacationService.getById(id)
    user match {
      case userM: Some[SkillModel] => Ok(Json.toJson(userM))
      case None => NotFound("No vacation with id")
    }*/
    NotFound
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    /*val jsonUserFromBody = request.body.as[SkillModel]

    val addResult: String = vacationService.create(jsonUserFromBody)
    Ok(Json.toJson(addResult))*/
    NotFound
  }

  def delete(userId: Long) = Action {
    /*val deletedUserId: Int = vacationService.delete(userId)
    if(deletedUserId == userId) {
      Ok("Vacation deleted")
    } else {
      NotFound("No vacation with id for deletion")
    }*/
    NotFound
  }
}