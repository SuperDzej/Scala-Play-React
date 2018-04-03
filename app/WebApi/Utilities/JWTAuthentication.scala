package WebApi.Utilities

import javax.inject.Inject
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}
import DAL.Models._
import DAL.Repository.UserRepository
import WebApi.Models.UserJwtPayload

import scala.concurrent._
import scala.concurrent.duration._

case class UserRequest[A](userInfo: User, request: Request[A]) extends WrappedRequest(request)

class JWTAuthentication @Inject() (parser: BodyParsers.Default, userRepository: UserRepository,
                                   jwtUtility: JwtUtility)(implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {
  private implicit val objectReads:Reads[UserJwtPayload] = Json.reads[UserJwtPayload]

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]):Future[Result] = {
    val jwtTokenAuth = request.headers.get("Authorization").getOrElse("")
    val jwtToken = jwtTokenAuth.replaceAll("(Bearer|bearer)" + " ", "")

    if (jwtUtility.isValidToken(jwtToken)) {
      jwtUtility.decodePayload(jwtToken).fold {
        Future.successful(Unauthorized("Invalid credential wrong payload"))
      } { payload =>
        val userCredentialsValidation: JsResult[UserJwtPayload] = Json.parse(payload).validate[UserJwtPayload]

        userCredentialsValidation match {
          case jsUserCredentials: JsSuccess[UserJwtPayload] =>
            val userCredentials: UserJwtPayload = jsUserCredentials.get

            val userInfoF: Future[Option[User]] = userRepository.getByEmail(userCredentials.email)
            val userInfo: Option[User] = Await.result(userInfoF, 3.seconds)

            userInfo match {
              case Some(user) =>
                if (user.id == userCredentials.userId)
                  block(UserRequest(user, request))
                else
                  Future.successful(Unauthorized("Invalid credential, invalid id"))
              case None =>
                Future.successful(Unauthorized("Invalid credential"))
            }
          case e: JsError => Future.successful(Unauthorized("Invalid token payload"))
        }
      }
    } else {
      Future.successful(Unauthorized("Invalid credential, invalid token"))
    }
  }
}

