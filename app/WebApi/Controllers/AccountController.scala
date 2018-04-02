package controllers

import javax.inject._
import java.io.File
import java.io.FileInputStream

import play.api.libs.json.Json
import play.api.mvc._

import akka.actor._
import akka.stream.scaladsl.{Source, StreamConverters, Flow , Sink}

import scala.concurrent._
import scala.concurrent.duration._

import WebApi.utilities.{JwtUtility, JWTAuthentication}

import DAL.Models._

import BLL.Models._
import BLL.Services._

import DAL.Repository._

case class UserCredentials(email: String, password: String)

@Singleton
class AccountController @Inject()(cc: ControllerComponents, jwtAuthentication: JWTAuthentication, jwtUtility: JwtUtility) 
  extends AbstractController(cc) {

  private implicit val credentialsRead = Json.reads[UserCredentials]
  def index = jwtAuthentication { request => 
    Ok("User authenticated")
  }

  def generateToken = Action(parse.json) { request =>
    val jsonUserCredentialsFromBody = request.body.as[UserCredentials]
    val token = jwtUtility.createToken("""{"email":"test@example.com","userId":"userId123"}""")

    Ok(token)
  }


}
