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
    val leaves:Seq[LeaveModel] = vacationService.get
    Ok(new RestResponse(Json.toJson(leaves), None).toJson)
  }

  def getById(id: Long) = Action {
    val leaveM: Option[LeaveModel] = vacationService.getById(id)
    leaveM match {
      case leave: Some[LeaveModel] => Ok(Json.toJson(leave))
      case None => NotFound("No leave with id")
    }
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val validateVacationBody = request.body.validate[LeaveModel]
    if(validateVacationBody.isSuccess) {
      val leaveModel = request.body.as[LeaveModel]
      val createLeaveResult = vacationService.create(leaveModel)

      if(createLeaveResult.isSuccess) Created(Json.toJson(createLeaveResult.result))
      else BadRequest(createLeaveResult.message)
    } else {
      BadRequest("Invalid data sent")
    }
  }

  def delete(id: Long) = Action {
    val deletedLeaveId: Int = vacationService.delete(id)

    if(deletedLeaveId == id) Ok("Leave deleted")
    else NotFound("No leave with id for deletion")
  }

}