package play.river

import play.hex.vertices.Point
import play.hex.Hex

case class RiverSegment(flow: Flow)

case class Flow(from: Point, to: Point)