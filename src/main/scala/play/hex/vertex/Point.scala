package play.hex.vertex

import play.hex.{BordersHex, HexPosition, side}

case class Point private(hexPosition: HexPosition, vertex: Vertex) {

  lazy val edges: Set[BordersHex] =
    if (vertex == E) Set(
      BordersHex(hexPosition, side.NE),
      BordersHex(hexPosition, side.SE),
      BordersHex(hexPosition.neighbourAt(side.NE), side.S)
    ) else Set(
      BordersHex(hexPosition, side.SE),
      BordersHex(hexPosition, side.S),
      BordersHex(hexPosition.neighbourAt(side.SE), side.SW)
    )
}

object Point {

  var cache: Map[(HexPosition, Vertex), Point] = Map.empty

  def fetchFromCache(hexPosition: HexPosition, vertex: Vertex): Point = {
    val key = (hexPosition, vertex)
    cache.get(key) match {
      case Some(point) => point
      case _ =>
        val point = new Point(hexPosition, vertex)
        cache = cache.updated(key, point)
        point
    }
  }

  /**
    * There are two possible configurations of a point with respect to the surrounding hexes.
    *
    * 1)  \_       or  2)  _/
    * .  /                  \
    *
    * Normalize Point so that in configuration:
    * (1) the hexPosition is chosen with vertex E
    * (2) the hexPosition is chosen with vertex SE
    */
  def apply(hexPosition: HexPosition, vertex: Vertex): Point = {

    vertex match {
      case SW => fetchFromCache(hexPosition.neighbourAt(side.SW), E)
      case NW => fetchFromCache(hexPosition.neighbourAt(side.NW), E)
      case E => fetchFromCache(hexPosition, E)
      // configuration 2:
      case W => fetchFromCache(hexPosition.neighbourAt(side.NW), SE)
      case NE => fetchFromCache(hexPosition.neighbourAt(side.N), SE)
      case SE => fetchFromCache(hexPosition, SE)
    }
  }
}
