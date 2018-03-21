package play.hex

import play.BordersHex

trait StoreableByEdge[T] extends StoreableByPosition[BordersHex, T] {

}

class StoreByEdge[T](
                     private[hex] var byPosition: Map[BordersHex, T]
                   ) extends StoreableByEdge[T] {
}
