package play.hex

trait StoreableByHexPosition[T] extends StoreableByPosition[HexPosition, T] {
  def at(x: Int, y: Int): Option[T] = byPosition.get(HexPosition(x, y))
}

class StoreByHexPosition[T](
                             private[hex] var byPosition: Map[HexPosition, T]
                           ) extends StoreableByHexPosition[T] {
}

class HexStore(
                               private[hex] var byPosition: Map[HexPosition, Hex],
                             ) extends StoreableByHexPosition[Hex] {

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

  private def hexPosToNeighbours(hexPos: HexPosition) =
    for {
      pos <- hexPos.neighbours
      hex <- at(pos)
    } yield hex

  private def hexPosToNeighboursWithSide(hexPos: HexPosition) =
    for {
      (side, pos) <- hexPos.neighboursWithSides
      hex <- at(pos)
    } yield (side, hex)

  def neighbours(hex: Hex): Iterable[Hex] =
    toPos(hex)
      .map(hexPosToNeighbours)
      .getOrElse(Nil)

  def neighbourMap(hex: Hex): Map[Side, Hex] =
    toPos(hex)
      .map(hexPosToNeighboursWithSide(_).toMap)
      .getOrElse(Map.empty)

}