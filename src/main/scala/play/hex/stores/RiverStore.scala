package play.hex.stores

import play.hex.{BordersHex, Hex}
import play.hex.vertex.Point
import play.river.RiverSegment

class RiverStore(private[hex] var byPosition: Map[BordersHex, RiverSegment]) extends StorableByEdge[RiverSegment] {

  def neighbours(edge: BordersHex): Map[BordersHex, RiverSegment] = {
    atPos(edge) match {
      case Some(river) => (river.edges - edge)
        .map(e => (e, atPos(e)))
          .collect({case (e, Some(r)) => (e, r)})
          .toMap
      case _ => Map.empty
    }
  }

}
