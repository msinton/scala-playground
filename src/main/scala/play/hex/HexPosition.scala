package play.hex

final case class HexPosition(x: Int, y: Int) extends Ordered[HexPosition] {

  import scala.math.Ordered.orderingToOrdered

  override def compare(that: HexPosition): Int = (x, y) compare (that.x, that.y)

  private val isEvenCol = y % 2 == 0

  private def nextWhenEvenCol(s: Side): HexPosition = s match {
    case N => this.copy(x = x - 1)
    case NW => this.copy(y = y - 1)
    case NE => this.copy(y = y + 1)
    case S => this.copy(x = x + 1)
    case SW => HexPosition(x + 1, y - 1)
    case SE => HexPosition(x + 1, y + 1)
  }

  private def nextWhenOddCol(s: Side): HexPosition = s match {
    case N => this.copy(x = x - 1)
    case NE => HexPosition(x - 1, y + 1)
    case NW => HexPosition(x - 1, y - 1)
    case SW => this.copy(y = y - 1)
    case SE => this.copy(y = y + 1)
    case S => this.copy(x = x + 1)
  }

  // TODO consider Flyweight to prevent GC
  val next: Side => HexPosition = if (isEvenCol) nextWhenEvenCol else nextWhenOddCol

  def neighbours: Iterable[HexPosition] = Sides.seq.map(next)

  def neighboursWithSides: Iterable[(Side, HexPosition)] = Sides.seq.map(s => (s, next(s)))
}

