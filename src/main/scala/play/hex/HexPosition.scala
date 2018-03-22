package play.hex

final case class HexPosition(x: Int, y: Int) extends Ordered[HexPosition] {

  import scala.math.Ordered.orderingToOrdered

  override def compare(that: HexPosition): Int = (x, y) compare (that.x, that.y)

  private val isEvenCol = y % 2 == 0

  lazy val neighbourMap: Map[Side, HexPosition] = {
    if (isEvenCol) Map(
      N -> HexPosition(x - 1, y),
      NW -> HexPosition(x, y - 1),
      NE -> HexPosition(x, y + 1),
      S -> HexPosition(x + 1, y),
      SW -> HexPosition(x + 1, y - 1),
      SE -> HexPosition(x + 1, y + 1))
    else Map(
      N -> HexPosition(x - 1, y),
      NE -> HexPosition(x - 1, y + 1),
      NW -> HexPosition(x - 1, y - 1),
      SW -> HexPosition(x, y - 1),
      SE -> HexPosition(x, y + 1),
      S -> HexPosition(x + 1, y))
  }

  def neighbourAt(side: Side): HexPosition = neighbourMap(side)

  lazy val neighbours: Iterable[HexPosition] =
    neighbourMap.values

}

object HexPosition {

  var cache: Map[(Int, Int), HexPosition] = Map.empty

  def apply(x: Int, y: Int): HexPosition = {
    val key = (x, y)
    cache.get(key) match {
      case Some(p) => p
      case _ =>
        val p = new HexPosition(x, y)
        cache = cache.updated(key, p)
        p
    }
  }

}