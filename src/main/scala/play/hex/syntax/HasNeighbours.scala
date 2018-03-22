package play.hex.syntax

trait HasNeighbours[A] {
  def neighbours(value: A): Iterable[A]
}

trait HasNeighbourMap[K, A] {
  def neighbourMap(hex: A): Map[K, A]
}
