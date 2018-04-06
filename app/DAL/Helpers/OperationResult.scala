package DAL.Helpers

case class OperationResult[R](isSuccess: Boolean, message: String, operationObject: Option[R])
