package play.hex

import scala.languageFeature.reflectiveCalls
import play.utils.HasWeighting

sealed trait HexType extends HasWeighting {
    val flooded: Boolean
    val lava: Boolean
    def flood: HexType
    def lavaFlow: HexType
    def drain: HexType
    def cool: HexType
}

case class Clay(weighting: Int = 10, flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Ore(weighting: Int = 12, flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Plains(weighting: Int = 25, flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Stone(weighting: Int = 15, flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Woods(weighting: Int = 20, flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Water(weighting: Int = 7, flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}