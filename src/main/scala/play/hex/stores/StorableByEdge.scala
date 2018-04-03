package play.hex.stores

import play.hex.BordersHex

trait StorableByEdge[T] extends StorableByPosition[BordersHex, T] {

}

class StoreByEdge[T](
                     private[hex] var byPosition: Map[BordersHex, T]
                   ) extends StorableByEdge[T] {
}
