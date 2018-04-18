package WebApi.Controllers

import BLL.Models.ProjectModel
import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Services._
import DAL.Repository.ProjectRepository

@Singleton
class ProjectController @Inject()(cc: ControllerComponents, projectService: ProjectService,
                                  projectRepository: ProjectRepository)
  extends AbstractController(cc) {

  def getById(id: Long) = Action {
    val project: Option[ProjectModel] = projectService.getById(id)
    project match {
      case userM: Some[ProjectModel] => Ok(Json.toJson(userM))
      case None => NotFound
    }
  }

  def get = Action {
    val projects = projectService.get
    Ok(Json.toJson(projects))
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val jsonUserFromBody = request.body.as[ProjectModel]

    val addResult:String = projectService.create(jsonUserFromBody)
    Ok(addResult)
  }

  def addSkills(id: Long): Action[JsValue] = Action(parse.json) { request =>
    val jsonSkillsFromBody = request.body.as[Seq[Long]]

    val addResult:String = projectService.addSkills(id, jsonSkillsFromBody)
    Ok(addResult)
  }

  def delete(id: Long) = Action {
    val deletedProjectId: Long = projectService.delete(id)
    if(deletedProjectId == id) {
      Ok("Project deleted")
    } else {
      NotFound("No project with id for deletion")
    }
  }
}

