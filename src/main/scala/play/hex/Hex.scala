package play.hex

// TODO remove boardData ...
final case class Hex protected(id: Int, hexType: HexType, hasVolcano: Boolean = false) {

  def flood: Hex = this.copy(hexType = hexType.flood)
  def lavaFlow: Hex = this.copy(hexType = hexType.lavaFlow)

  def getSide(hex: Hex): Option[Side] = ???
}
