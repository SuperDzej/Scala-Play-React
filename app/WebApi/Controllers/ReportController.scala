package WebApi.Controllers

import BLL.Models.UserReportModel
import BLL.Services.UserReportService
import javax.inject._
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import play.api.mvc._


@Singleton
class ReportController @Inject()(cc: ControllerComponents, userReportService: UserReportService)
  extends AbstractController(cc) {

  def get = Action {
    val reports = userReportService.get
    Ok(Json.toJson(reports))
  }

  def getById(id: Long) = Action {
    val report = userReportService.getById(id)
    report match {
      case Some(rep) => Ok(Json.toJson(rep))
      case None => NotFound("No report with selected id")
    }
  }

  def post: Action[JsValue]  = Action(parse.json) { request =>
    val jsonReportFromBody = request.body.as[UserReportModel]
    val createdMessage = userReportService.create(jsonReportFromBody)
    Ok(createdMessage)
  }

  def delete(userId: Long) = Action {
    val deletedUserId: Int = userReportService.delete(userId)
    if(deletedUserId == userId) {
      Ok("User report deleted")
    } else {
      NotFound("No user report with id for deletion")
    }
  }
}
