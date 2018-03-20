package play.hex

// TODO remove boardData ...
final case class Hex protected(id: Int, hexType: HexType, hasVolcano: Boolean = false, private val boardData: BoardData) {

  def flood(): Hex = this.copy(hexType = hexType.flood)
  def lavaFlow(): Hex = this.copy(hexType = hexType.lavaFlow)

  def neighbours: Map[Side, Hex] = boardData.hexPosition(this)
    .map(hexPos => (for {
      (side, pos) <- hexPos.neighboursWithSides
      hex <- boardData.hexAt(pos)
    } yield (side, hex)).toMap
    ).getOrElse(Map.empty)

  def getSide(hex: Hex): Option[Side] = ???
}


class BoardData {

  private var hexesByPosition = Map.empty[HexPosition, Hex]
  private var hexesToPosition = Map.empty[Hex, HexPosition]
  private var hexesByType = Map.empty[HexType, Iterable[(HexPosition, Hex)]]

  private def setupHexes(): Iterable[(HexPosition, Hex)] = ???

  private val hexes = setupHexes()
  hexesByPosition = hexes.toMap
  hexesToPosition = hexes.map(_.swap).toMap



  def hexPosition(hex: Hex): Option[HexPosition] = hexesToPosition.get(hex)
  // rivers
  // bridges
  // boats
  // people
  // walls

  def hexAt(x: Int, y: Int): Option[Hex] = hexesByPosition.get(HexPosition(x, y))
  def hexAt(pos: HexPosition): Option[Hex] = hexesByPosition.get(pos)

  def neighbours(hex: Hex, side: Side): List[Hex] = {

    def evenColNeighbours(p: HexPosition) =
        hexAt(p.x - 1, p.y    ) ++ // N
        hexAt(p.x    , p.y - 1) ++ // NW
        hexAt(p.x    , p.y + 1) ++ // NE
        hexAt(p.x + 1, p.y    ) ++ // S
        hexAt(p.x + 1, p.y - 1) ++ // SW
        hexAt(p.x + 1, p.y + 1)    // SE

    def oddColNeighbours(p: HexPosition) =
        hexAt(p.x - 1, p.y    ) ++ // N
        hexAt(p.x - 1, p.y + 1) ++ // NE
        hexAt(p.x - 1, p.y - 1) ++ // NW
        hexAt(p.x    , p.y - 1) ++ // SW
        hexAt(p.x    , p.y + 1) ++ // SE
        hexAt(p.x + 1, p.y    )    // S

    hexesToPosition.get(hex).toList.flatMap(p =>
      if (p.y % 2 == 0) evenColNeighbours(p)
      else oddColNeighbours(p)
    )
  }

  def floodHex(hex: Hex): Unit = {
    hexesToPosition.get(hex).foreach(p => swapHexAt(p, hex, hex.flood()))
  }

  def lavaFieldOnHex(hex: Hex): Unit = {
    hexesToPosition.get(hex).foreach(p => swapHexAt(p, hex, hex.flood()))
  }

  def swapHexAt(p: HexPosition, oldHex: Hex, newHex: Hex): Unit = {
    hexesToPosition -= oldHex
    hexesToPosition = hexesToPosition.updated(newHex, p)
    hexesByPosition = hexesByPosition.updated(p, newHex)
  }
}