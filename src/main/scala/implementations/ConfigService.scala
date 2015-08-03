package implementations

import com.typesafe.config.ConfigFactory

trait ConfigService {
  val config = ConfigFactory.load()
}
