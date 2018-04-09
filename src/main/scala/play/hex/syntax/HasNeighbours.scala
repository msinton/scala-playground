package play.hex.syntax

import play.hex.BordersHex
import play.hex.graph.side.Side

trait HasNeighbours[A] {
  def neighbours(value: A): Iterable[A]
}

trait HasNeighbourMap[K, A] {
  def neighbourMap(value: A): Map[K, A]
}

trait HasSides[A] {
  def sideBetween(value: A, neighbour: A): Option[Side]
}

trait HasEdges[A] {
  def edges(value: A): Iterable[BordersHex]
}