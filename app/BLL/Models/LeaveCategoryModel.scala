package BLL.Models

import play.api.libs.json.{Json, OWrites, Reads, Writes}

case class LeaveCategoryModel (id: Option[Long], name: String)

object LeaveCategoryModel {
  implicit val vacationsRead: Reads[LeaveCategoryModel] = Json.reads[LeaveCategoryModel]
  implicit val vacationsWrite: OWrites[LeaveCategoryModel] = Json.writes[LeaveCategoryModel]
}
