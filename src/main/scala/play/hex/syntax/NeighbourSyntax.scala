package play.hex.syntax

import play.hex.BordersHex
import play.hex.graph.side.Side

/**
  * Provides the implicits in order to use neighbour syntax on HasNeighbours instances.
  *
  * e.g
  * hex.neighbours
  * (even though hex does not have the def, the hex is implicitly converted into a NeighbourOps[Hex])
  */
trait NeighbourSyntax {

  implicit class NeighbourOps[A](value: A) {

    def neighbours(implicit a: HasNeighbours[A]): Iterable[A] =
      a.neighbours(value)

    def sideBetween(b: A)(implicit a: HasSides[A]): Option[Side] =
      a.sideBetween(value, b)

    def edges(implicit a: HasEdges[A]): Iterable[BordersHex] =
      a.edges(value)
  }

  implicit class NeighbourMapOps[K, A](value: A) {

    def neighbourMap(implicit a: HasNeighbourMap[K, A]): Map[K, A] =
      a.neighbourMap(value)
  }
}