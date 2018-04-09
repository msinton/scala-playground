package play.hex.stores

import play.hex.graph.HexPosition
import play.hex.{Hex, HexType}


class HexStore(private[hex] var byPosition: Map[HexPosition, Hex]) extends StorableByHexPosition[Hex] {

  private[hex] var _byType: Map[HexType, Map[HexPosition, Hex]] =
    byPosition
      .groupBy(_._2.hexType)
      .mapValues(_.toMap)
      .withDefaultValue(Map.empty)

  override def removeCustom(p: HexPosition, hex: Hex): Unit = {
    _byType = _byType.updated(hex.hexType, _byType(hex.hexType) - p)
  }

  override def updateCustom(p: HexPosition, newHex: Hex, oldHex: Hex): Unit = {
    if (newHex.hexType != oldHex.hexType) {
      removeCustom(p, oldHex)
    }
  }

  override def updateCustom(p: HexPosition, hex: Hex): Unit = {
    _byType = _byType.updated(hex.hexType, _byType(hex.hexType).updated(p, hex))
  }

  def byType: Map[HexType, Map[HexPosition, Hex]] = _byType

}