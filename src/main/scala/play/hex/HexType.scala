package play.hex

sealed trait HexType {
  val flooded: Boolean
  val lava: Boolean

  def flood: HexType

  def lavaFlow: HexType

  def drain: HexType

  def cool: HexType
}

case class Clay(flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Ore(flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Plains(flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Stone(flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Woods(flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}

case class Water(flooded: Boolean = false, lava: Boolean = false) extends HexType {
  override def flood: HexType = this.copy(flooded = true)

  override def lavaFlow: HexType = this.copy(lava = true)

  override def drain: HexType = this.copy(flooded = false)

  override def cool: HexType = this.copy(lava = false)
}
