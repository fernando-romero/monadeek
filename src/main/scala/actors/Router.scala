package actors

import marshalling._
import services._
import messages._

import akka.actor.{Actor, Props}
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
import spray.http.StatusCodes._

object Router {
  def props(ds: DatabaseService): Props = Props(new Router(ds))
}

class Router(val ds: DatabaseService) extends Actor with HttpService with JsonMarshalling {

  def actorRefFactory = context

  def receive = runRoute(
    path("health") {
      complete {
        OK
      }
    } ~
    path("users") {
      post {
        entity(as[CreateUser]) { cu =>
          complete(cu)
        }
      }
    }
  )
}
