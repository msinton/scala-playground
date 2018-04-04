package play.hex.stores

import play.hex.BordersHex
import play.hex.syntax.HasNeighbourMap
import play.river.RiverSegment

import scala.util.Random

class RiverStore(private[hex] var byPosition: Map[BordersHex, RiverSegment], random: Random) extends StorableByEdge[RiverSegment] {

  def neighbourMap(edge: BordersHex): Map[BordersHex, RiverSegment] = {
    atPos(edge) match {
      case Some(river) => (river.edges - edge)
        .map(e => (e, atPos(e)))
          .collect({case (e, Some(r)) => (e, r)})
          .toMap
      case _ => Map.empty
    }
  }

}
