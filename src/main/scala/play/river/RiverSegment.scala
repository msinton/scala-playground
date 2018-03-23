package play.river

import play.hex.vertex.Point

case class RiverSegment(flow: Flow)

case class Flow(from: Point, to: Point)