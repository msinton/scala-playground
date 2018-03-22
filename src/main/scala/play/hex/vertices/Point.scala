package play.hex.vertices

import play.hex.{HexPosition, side}

//TODO get rid of id?
case class Point(hexPosition: HexPosition, vertex: Vertex)

object Point {

  /**
    * There are two possible configurations of a point with respect to the surrounding hexes.
    *
    * 1)  \_       or  2)  _/
    *    /                  \
    *
    * Normalize Point so that in configuration:
    * (1) the hexPosition is chosen with vertex E
    * (2) the hexPosition is chosen with vertex SE
    */
  def apply(hexPosition: HexPosition, vertex: Vertex): Point = {

    vertex match {
      case SW => new Point(hexPosition.neighbourAt(side.SW), E)
      case NW => new Point(hexPosition.neighbourAt(side.NW), E)
      case E => new Point(hexPosition, E)
        // configuration 2:
      case W => new Point(hexPosition.neighbourAt(side.NW), SE)
      case NE => new Point(hexPosition.neighbourAt(side.N), SE)
      case SE => new Point(hexPosition, SE)
    }
  }
}
