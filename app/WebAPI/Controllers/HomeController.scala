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

import DAL.Models._

import BLL.Models._
import BLL.Services._

import DAL.Repository._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) 
  extends AbstractController(cc) {

  def index = Action {
    Ok("Welcome to index action")
  }
}
