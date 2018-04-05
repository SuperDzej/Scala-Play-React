package WebApi.Controllers

import BLL.Services.UserService
import DAL.Models.UserReport
import javax.inject._
import play.api.libs.json.{JsValue, Json, Reads}
import play.api.mvc._


@Singleton
class ReportController @Inject()(cc: ControllerComponents, ussc: UserService)
  extends AbstractController(cc) {
  private implicit val reportReads: Reads[UserReport] = Json.reads[UserReport]
  def index = Action {
    Ok("Welcome to report action")
  }

  def get = Action {
    NotFound
  }

  def report: Action[JsValue]  = Action(parse.json) { request =>
    val jsonReportFromBody = request.body.as[UserReport]
    println(jsonReportFromBody)
    NotFound
  }
}
