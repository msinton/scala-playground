package play.hex.stores

import play.hex.BordersHex
import play.hex.graph.vertex.Point
import play.river.RiverSegment

import scala.util.Random

class RiverStore(private[hex] var byPosition: Map[BordersHex, RiverSegment], random: Random)
  extends StorableByEdge[RiverSegment] {

  def neighbourMap(edge: BordersHex): Map[BordersHex, RiverSegment] = {
    atPos(edge) match {
      case Some(river) => (river.edges - edge)
        .map(e => (e, atPos(e)))
          .collect({case (e, Some(r)) => (e, r)})
          .toMap
      case _ => Map.empty
    }
  }

  def edgesWithRiversFlowingFrom(point: Point): Set[BordersHex] =
    point.edges.filter(atPos(_).exists(_.flow.from == point))

}
