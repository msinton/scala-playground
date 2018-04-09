package play

import play.hex.graph.{HexPosition, Rotation}
import play.hex.graph.syntax.{GraphSyntax, Rotates}
import play.hex.graph.vertex.{Point, Vertex}
import play.hex.syntax.NeighbourSyntax
import play.random.RichRandom

import scala.util.Random
import scala.language.implicitConversions

package object implicits extends
  NeighbourSyntax with
  GraphSyntax {

  implicit def randomWrapper(r: Random): RichRandom = new RichRandom(r)

  implicit val hexPosRotatesPoint: Rotates[HexPosition, Vertex, Point] =
    (hexPosition: HexPosition, rotation: Rotation) => Point.rotate(hexPosition)(rotation)
}
