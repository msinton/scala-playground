package play

import play.random.RichRandom

import scala.util.Random
import scala.language.implicitConversions

package object implicits {

  implicit def randomWrapper(r: Random): RichRandom = new RichRandom(r)
}
