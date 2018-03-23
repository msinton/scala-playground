package play.hex

import play.hex.side._
import play.hex.vertex.Point

// TODO rename as Edge
case class BordersHex private(hexPosition: HexPosition, side: Side) {

  lazy val points = Set(
    Point(hexPosition, side.clockwiseVertex),
    Point(hexPosition, side.antiClockwiseVertex)
  )

  def otherPoint(point: Point): Point = (points - point).head

}

object BordersHex {

  var cache: Map[(HexPosition, Side), BordersHex] = Map.empty

  /**
    * Normalize BordersHex so that Side is only ever S, SE or SW.
    * Then equality works as expected without needing to be overridden.
    */
  def apply(p: HexPosition, side: Side): BordersHex = {

    val key = (p, side)
    cache.get(key) match {
      case Some(e) => e
      case _ =>
        val edge = side match {
          case N => new BordersHex(p.neighbourAt(N), S)
          case NE => new BordersHex(p.neighbourAt(NE), SW)
          case NW => new BordersHex(p.neighbourAt(NW), SE)
          case _ => new BordersHex(p, side)
        }
        cache = cache.updated(key, edge)
        edge
    }
  }
}