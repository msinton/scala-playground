package play.river

import play.hex.BordersHex
import play.hex.vertex.Point

case class RiverSegment(flow: Flow) {

  lazy val edges: Set[BordersHex] = flow.from.edges ++ flow.to.edges
}

object RiverSegment {

  def apply(flowFrom: Point, edge: BordersHex): RiverSegment =
    edge.otherPoint(flowFrom) match {
      case Some(flowTo) => RiverSegment(Flow(flowFrom, flowTo))
    }

}

case class Flow(from: Point, to: Point)