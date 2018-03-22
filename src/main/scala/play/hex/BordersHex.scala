package play.hex

import play.hex.side._

// TODO rename as Edge
case class BordersHex(hexPosition: HexPosition, side: Side)

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