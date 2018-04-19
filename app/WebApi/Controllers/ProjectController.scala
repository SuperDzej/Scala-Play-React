package WebApi.Controllers

import BLL.Models.ProjectModel
import javax.inject._
import play.api.libs.json._
import play.api.mvc._
import BLL.Services._
import DAL.Repository.ProjectRepository
import WebApi.Models.RestResponse

@Singleton
class ProjectController @Inject()(cc: ControllerComponents,
                                  projectService: ProjectService,
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
    Ok(Json.toJson(new RestResponse(Json.toJson(projects), None).toJson))
  }

  def post: Action[JsValue] = Action(parse.json) { request =>
    val projectModelValidation = request.body.validate[ProjectModel]
    if(projectModelValidation.isSuccess) {
      val projectModel = request.body.as[ProjectModel]
      Created(Json.toJson(projectService.create(projectModel)))
    } else {
      BadRequest("Invalid data sent")
    }
  }

  def addSkills(id: Long): Action[JsValue] = Action(parse.json) { request =>
    val skills = request.body.as[Seq[Long]]

    Ok(projectService.addSkills(id, skills))
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

