package play.hex.graph.vertex

import play.hex.graph._
import play.hex.BordersHex

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

  lazy val hexPositions: Set[HexPosition] =
    if (vertex == E) Set(
      hexPosition,
      hexPosition.neighbourAt(side.NE),
      hexPosition.neighbourAt(side.SE)
    ) else Set(
      hexPosition,
      hexPosition.neighbourAt(side.SE),
      hexPosition.neighbourAt(side.S)
    )

  def edgeBetween(point: Point): Option[BordersHex] =
    edges.find(_.points.contains(point))

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

  def clockwise(hexPosition: HexPosition, from: Vertex): Point =
    apply(hexPosition, from.clockwise)

  def antiClockwise(hexPosition: HexPosition, from: Vertex): Point =
    apply(hexPosition, from.antiClockwise)

  def rotate(hexPosition: HexPosition)(rotation: Rotation)(from: Vertex): Point = rotation match {
    case Clockwise => clockwise(hexPosition, from)
    case AntiClockwise => antiClockwise(hexPosition, from)
  }

}
