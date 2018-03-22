package play.hex.stores

import play.hex.BordersHex

trait StoreableByEdge[T] extends StoreableByPosition[BordersHex, T] {

}

class StoreByEdge[T](
                     private[hex] var byPosition: Map[BordersHex, T]
                   ) extends StoreableByEdge[T] {
}


//  final def addRiver(hexA: Hex, sideA: Side): Option[RiverSegment] = {
//    hexes.toPos(hexA).map(p => {
//      val edge = BordersHex(p, sideA)
//      val river = RiverSegment()
//      _riverMap = _riverMap.updated(edge, river)
//      river
//    })
//  }