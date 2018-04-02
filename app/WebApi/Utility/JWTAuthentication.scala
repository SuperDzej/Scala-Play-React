package WebApi.utilities

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc._
import play.api.mvc.Results._
import WebApi.utilities.JwtUtility

import scala.concurrent.{Future, ExecutionContext}


import DAL.Models._

case class UserJwtPayload(email: String, userId: String)

case class UserRequest[A](userInfo: User, request: Request[A]) extends WrappedRequest(request)

class DataSource {
  def getUser(email: String, userId: String): Option[User] =
    if (email == "test@example.com" && userId == "userId123") {
      Some(User(1, "John", "Nash", 387623445565L, email, "scala123AZ"))
    } else {
      None
    }
}

class JWTAuthentication @Inject() (parser: BodyParsers.Default, dataSource: DataSource, 
  jwtUtility: JwtUtility)(implicit ec: ExecutionContext) 
  extends ActionBuilderImpl(parser) {
  private implicit val objectReads = Json.reads[UserJwtPayload]

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    val jwtTokenAuth = request.headers.get("Authorization").getOrElse("")
    val jwtToken = jwtTokenAuth.replaceAll("(Bearer|bearer)" + " ", "")
    println("token: " + jwtToken)
    if (jwtUtility.isValidToken(jwtToken)) {
      jwtUtility.decodePayload(jwtToken).fold {
        Future.successful(Unauthorized("Invalid credential wrong payload"))
      } { payload =>
        val userCredentials = Json.parse(payload).validate[UserJwtPayload].get

        // Replace this block with data source
        val maybeUserInfo = dataSource.getUser(userCredentials.email, userCredentials.userId)

        maybeUserInfo.fold(Future.successful(Unauthorized("Invalid credential")))(userInfo => block(UserRequest(userInfo, request)))
      }
    } else {
      Future.successful(Unauthorized("Invalid credential, invalid token"))
    }
  }
}

