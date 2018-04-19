package WebApi.Controllers

import BLL.Services.AuthenticationService
import WebApi.Models.{JwtToken, UserCredentials}
import javax.inject._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import WebApi.Utilities.{JWTAuthentication, JwtUtility}

@Singleton
class AccountController @Inject()(cc: ControllerComponents,
                                  jwtAuthentication: JWTAuthentication,
                                  jwtUtility: JwtUtility,
                                  authService: AuthenticationService)
  extends AbstractController(cc) {

  def index = jwtAuthentication { _ =>
    Ok("User authenticated")
  }

  def generateToken: Action[JsValue] = Action(parse.json) { request =>
    val userCredentials = request.body.as[UserCredentials]
    val tokenPayload: Option[String] = authService.generateTokenPayload(userCredentials)
    tokenPayload match {
      case Some(payload) => Ok(Json.toJson(jwtUtility.createToken(payload)))
      case None => NotFound
    }
  }
}
