package marshalling

import models._
import messages._
import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport

trait JsonMarshalling extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val createUserFmt = jsonFormat1(CreateUser.apply)
}
