package play.hex.syntax

import play.hex.side.Side

trait HasNeighbours[A] {
  def neighbours(value: A): Iterable[A]
}

trait HasNeighbourMap[K, A] {
  def neighbourMap(hex: A): Map[K, A]
}

trait HasSides[A] {
  def sideBetween(value: A): Exp[A]
}

trait Exp[A] {
  def apply(n: A): Option[Side]
}