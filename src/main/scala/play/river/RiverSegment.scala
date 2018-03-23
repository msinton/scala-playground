package play.river

import play.hex.BordersHex
import play.hex.vertex.Point

case class RiverSegment(flow: Flow)

object RiverSegment {

  def apply(flowFrom: Point, edge: BordersHex): RiverSegment = {
    RiverSegment(Flow(flowFrom, edge.otherPoint(flowFrom)))
  }
}

case class Flow(from: Point, to: Point)