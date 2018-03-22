package play.hex.stores

import play.hex.{Hex, HexPosition, HexType}


class HexStore(private[hex] var byPosition: Map[HexPosition, Hex]) extends StoreableByHexPosition[Hex] {

  private[hex] var _byType: Map[HexType, Map[HexPosition, Hex]] =
    byPosition
      .groupBy(_._2.hexType)
      .mapValues(_.toMap)
      .withDefaultValue(Map.empty)

  override def removeCustom(p: HexPosition, hex: Hex): Unit = {
    _byType = _byType.updated(hex.hexType, _byType(hex.hexType) - p)
  }

  protected def insertCustom(p: HexPosition, hex: Hex): Unit = {
    _byType = _byType.updated(hex.hexType, _byType(hex.hexType).updated(p, hex))
  }

  override def updateCustom(p: HexPosition, newHex: Hex, oldHex: Hex): Unit = {
    if (newHex.hexType != oldHex.hexType) {
      removeCustom(p, oldHex)
    }
    insertCustom(p, newHex)
  }

  def byType: Map[HexType, Map[HexPosition, Hex]] = _byType

}