package play.hex.stores

import play.hex.BordersHex

trait StoreableByEdge[T] extends StoreableByPosition[BordersHex, T] {

}

class StoreByEdge[T](
                     private[hex] var byPosition: Map[BordersHex, T]
                   ) extends StoreableByEdge[T] {
}
