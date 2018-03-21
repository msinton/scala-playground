package play.hex

final case class Hex protected(id: Int, hexType: HexType, hasVolcano: Boolean = false) {

  def flood: Hex = this.copy(hexType = hexType.flood)
  def lavaFlow: Hex = this.copy(hexType = hexType.lavaFlow)
}
