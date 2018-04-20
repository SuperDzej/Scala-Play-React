package WebApi.Controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Models._
import BLL.Services._
import DAL.Repository.LeaveCategoryRepository
import WebApi.Models.RestResponse

@Singleton
class LeaveCategoryController @Inject()(cc: ControllerComponents,
                                vacationService: LeaveCategoryService,
                                vacationRepository: LeaveCategoryRepository)
  extends AbstractController(cc) {

  def get = Action {
    val vacations:Seq[LeaveCategoryModel] = vacationService.get
    Ok(new RestResponse(Json.toJson(vacations), None).toJson)
  }

  def getById(id: Long) = Action {
    val vacation: Option[LeaveCategoryModel] = vacationService.getById(id)
    vacation match {
      case vacationM: Some[LeaveCategoryModel] => Ok(Json.toJson(vacationM))
      case None => NotFound("No vacation with id")
    }
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val validateVacationBody = request.body.validate[LeaveCategoryModel]
    if(validateVacationBody.isSuccess) {
      val vacationModel = request.body.as[LeaveCategoryModel]
      Created(Json.toJson(vacationService.create(vacationModel)))
    } else {
      BadRequest("Invalid data sent")
    }
  }

  def delete(id: Long) = Action {
    val deletedVacationId: Int = vacationService.delete(id)
    if(deletedVacationId == id) {
      Ok("Leave category deleted")
    } else {
      NotFound("No leave category with id for deletion")
    }
  }

}