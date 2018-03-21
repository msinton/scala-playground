package play.hex

final case class HexPosition(x: Int, y: Int) extends Ordered[HexPosition] {

  import scala.math.Ordered.orderingToOrdered

  override def compare(that: HexPosition): Int = (x, y) compare (that.x, that.y)

  private val isEvenCol = y % 2 == 0

  private def nextWhenEvenCol(s: Side): HexPosition = s match {
    case N => HexPosition(x - 1, y)
    case NW => HexPosition(x, y - 1)
    case NE => HexPosition(x, y + 1)
    case S => HexPosition(x + 1, y)
    case SW => HexPosition(x + 1, y - 1)
    case SE => HexPosition(x + 1, y + 1)
  }

  private def nextWhenOddCol(s: Side): HexPosition = s match {
    case N => HexPosition(x - 1, y)
    case NE => HexPosition(x - 1, y + 1)
    case NW => HexPosition(x - 1, y - 1)
    case SW => HexPosition(x, y - 1)
    case SE => HexPosition(x, y + 1)
    case S => HexPosition(x + 1, y)
  }

  val next: Side => HexPosition =
    if (isEvenCol) nextWhenEvenCol else nextWhenOddCol

  def neighbours: Iterable[HexPosition] =
    Sides.seq.map(next)

  def neighboursWithSides: Iterable[(Side, HexPosition)] =
    Sides.seq.map(s => (s, next(s)))
}

object HexPosition {

  import scala.collection.mutable
  import scala.ref.WeakReference

  val cache: mutable.Map[(Int, Int), WeakReference[HexPosition]] = mutable.Map.empty

  def apply(x: Int, y: Int): HexPosition = {
    cache.get((x, y)) match {
      case Some(WeakReference(p)) => p
      case _ =>
        val p = new HexPosition(x, y)
        cache.put((x, y), WeakReference(p))
        p
    }
  }

}