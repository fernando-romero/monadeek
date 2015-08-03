package services

import models._
import scala.concurrent.Future

trait DatabaseService {

  def setup: Future[Boolean]

  def getUserByEmail(email: String): Future[Option[User]]
  def saveUser(user: User): Future[User]
}
