package play.hex.syntax

import play.hex.side.Side

trait HasNeighbours[A] {
  def neighbours(value: A): Iterable[A]
}

trait HasNeighbourMap[K, A] {
  def neighbourMap(value: A): Map[K, A]
}

trait HasSides[A] {
  def sideBetween(value: A, neighbour: A): Option[Side]
}
