package BLL.Models

import play.api.libs.json.{Json, Reads, Writes}

case class SkillModel (id: Option[Long], name: String, description: String, level: String)

object SkillModel {
  implicit val credentialsRead: Reads[SkillModel] = Json.reads[SkillModel]
  implicit val credentialsWrite: Writes[SkillModel] = Json.writes[SkillModel]
}