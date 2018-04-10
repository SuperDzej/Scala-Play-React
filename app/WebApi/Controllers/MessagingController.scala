package WebApi.Controllers

import BLL.Models.UserReportModel
import BLL.Services.UserReportService
import javax.inject._
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import play.api.mvc._


@Singleton
class MessagingController @Inject()(cc: ControllerComponents, userReportService: UserReportService)
  extends AbstractController(cc) {

  private implicit val reportReads: Reads[UserReportModel] = Json.reads[UserReportModel]
  private implicit val reportWrites: Writes[UserReportModel] = Json.writes[UserReportModel]

  def get = Action {
    NotFound
  }
}