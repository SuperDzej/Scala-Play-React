package WebApi.Controllers

import BLL.Services.AuthenticationService
import WebApi.Models.{JwtToken, UserCredentials, UserJwtPayload}
import javax.inject._
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import play.api.mvc._
import WebApi.Utilities.{JWTAuthentication, JwtUtility}

@Singleton
class AccountController @Inject()(cc: ControllerComponents, jwtAuthentication: JWTAuthentication,
                                  jwtUtility: JwtUtility, authService: AuthenticationService)
  extends AbstractController(cc) {
  private implicit val credentialsRead: Reads[UserCredentials] = Json.reads[UserCredentials]
  private implicit val jwtTokenWrite: Writes[JwtToken] = Json.writes[JwtToken]
  def index = jwtAuthentication { request => 
    Ok("User authenticated")
  }

  def generateToken: Action[JsValue] = Action(parse.json) { request =>
    val userCredentials = request.body.as[UserCredentials]
    val tokenPayload: Option[String] = authService.generateTokenPayload(userCredentials)
    tokenPayload match {
      case Some(payload) =>
        val jwtToken: JwtToken = jwtUtility.createToken(payload)
        Ok(Json.toJson(jwtToken))
      case None => NotFound
    }
  }
}
