package WebApi.Controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Models._
import BLL.Services._
import DAL.Repository.LeaveRepository
import WebApi.Models.RestResponse

@Singleton
class LeaveController @Inject()(cc: ControllerComponents,
                                vacationService: LeaveService,
                                vacationRepository: LeaveRepository)
  extends AbstractController(cc) {

  def get = Action {
    val vacations:Seq[LeaveModel] = vacationService.get
    Ok(new RestResponse(Json.toJson(vacations), None).toJson)
  }

  def getById(id: Long) = Action {
    val vacation: Option[LeaveModel] = vacationService.getById(id)
    vacation match {
      case vacationM: Some[LeaveModel] => Ok(Json.toJson(vacationM))
      case None => NotFound("No vacation with id")
    }
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val validateVacationBody = request.body.validate[LeaveModel]
    if(validateVacationBody.isSuccess) {
      val vacationModel = request.body.as[LeaveModel]
      Created(Json.toJson(vacationService.create(vacationModel)))
    } else {
      BadRequest("Invalid data sent")
    }
  }

  def delete(id: Long) = Action {
    val deletedVacationId: Int = vacationService.delete(id)
    if(deletedVacationId == id) {
      Ok("Vacation deleted")
    } else {
      NotFound("No vacation with id for deletion")
    }
  }

}