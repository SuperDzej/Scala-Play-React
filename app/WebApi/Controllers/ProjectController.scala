package WebApi.Controllers

import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Models._
import BLL.Services._
import DAL.Helpers.OperationResult
import DAL.Models.Skill
import DAL.Repository.SkillRepository

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

@Singleton
class ProjectController @Inject()(cc: ControllerComponents, userService: UserService,
                                  skillRepository: SkillRepository)
  extends AbstractController(cc) {

  private implicit val skillWrites: Writes[Skill] = Json.writes[Skill]
  private implicit val skillReads: Reads[Skill] = Json.reads[Skill]

  def getById(userId: Long) = Action { _ =>
    val user: Option[UserModel] = userService.getById(userId)
    user match {
      case userM: Some[UserModel] => Ok(Json.toJson(userM))
      case None => NotFound
    }
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val jsonUserFromBody = request.body.as[Skill]

    val addResult:Future[OperationResult[Long]] = skillRepository.create(jsonUserFromBody)
    Ok(Json.toJson(addResult))
  }

  def delete(userId: Long) = Action {
    val deletedUserId: Int = Await.result(skillRepository.delete(userId), 2.seconds)
    if(deletedUserId == userId) {
      Ok("Skill deleted")
    } else {
      NotFound("No skill with id for deletion")
    }
  }
}