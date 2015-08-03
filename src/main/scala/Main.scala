import actors._
import services._
import implementations._

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import reactivemongo.api.{MongoConnection, MongoDriver}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import spray.can.Http

object Main extends App {

  val ds = new DatabaseService() with MongoDBService with ConfigService
  ds.setup.map { _ =>
    implicit val system = ActorSystem()
    implicit val timeout = Timeout(5.seconds)

    val listener = system.actorOf(Router.props(ds))
    val manager = IO(Http)

    val interface = ds.config.getString("monadeek.http.interface")
    val port = ds.config.getInt("monadeek.http.port")

    manager ? Http.Bind(listener, interface = interface, port = port)
  }
}
