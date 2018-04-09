package play.river

import play.hex.BordersHex
import play.hex.graph.vertex.Point

case class RiverSegment(flow: Flow) {

  lazy val edges: Set[BordersHex] = flow.from.edges ++ flow.to.edges

  def contains(point: Point): Boolean = flow.from == point || flow.to == point
}

object RiverSegment {

  def apply(flowFrom: Point, edge: BordersHex): RiverSegment =
    edge.otherPoint(flowFrom) match {
      case Some(flowTo) => RiverSegment(Flow(flowFrom, flowTo))
      case _ => throw new RuntimeException("Logic error: trying to create river segment with point not on edge")
    }

}

