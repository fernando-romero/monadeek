package implementations

import models._
import services._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.api.{MongoConnection, MongoDriver}
import reactivemongo.api.indexes.{Index, IndexType}

trait MongoDBService extends DatabaseService { _: ConfigService =>

  private lazy val db = {
    val uri = config.getString("mongodb.uri")
    val parsedUri = MongoConnection.parseURI(uri).getOrElse {
      throw new Exception("config mongodb.uri is bad formatted")
    }
    val dbName = parsedUri.db.getOrElse {
      throw new Exception("database name must be provided in mongodb.uri config")
    }
    val mongoDriver = new MongoDriver()
    mongoDriver.connection(parsedUri)(dbName)
  }

  def setup: Future[Boolean] = {
    val emailIndex = Index(key = Seq("email" -> IndexType.Ascending), unique = true)
    db("users").indexesManager.ensure(emailIndex)
  }

  def getUserByEmail(email: String): Future[Option[User]] = Future.successful(None)
  def saveUser(user: User): Future[User] = Future.successful(user)
}
