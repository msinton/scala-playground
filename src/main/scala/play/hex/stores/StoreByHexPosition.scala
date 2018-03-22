package play.hex.stores

import play.hex._
import play.hex.side.Side
import play.hex.syntax.{HasNeighbourMap, HasNeighbours}

trait StoreableByHexPosition[T] extends StoreableByPosition[HexPosition, T] {
  def at(x: Int, y: Int): Option[T] = byPosition.get(HexPosition(x, y))

  private def findNeighbourPosition(p: HexPosition, neighbourHex: T): Option[(Side, HexPosition)] =
    p.neighbourMap.find({
      case (_, neighbourP) => at(neighbourP).contains(neighbourHex)
    })

  def getSide(t: T, neighbour: T): Option[Side] =
    for {
      p <- toPos(t)
      (side, _) <- findNeighbourPosition(p, neighbour)
    } yield side

  private def hexPosToNeighbours(hexPos: HexPosition) =
    for {
      pos <- hexPos.neighbours
      t <- at(pos)
    } yield t

  private def hexPosToNeighboursWithSide(hexPos: HexPosition) =
    for {
      (side, pos) <- hexPos.neighbourMap
      t <- at(pos)
    } yield (side, t)

  def neighbours(t: T): Iterable[T] =
    toPos(t)
      .map(hexPosToNeighbours)
      .getOrElse(Nil)

  def neighbourMap(t: T): Map[Side, T] =
    toPos(t)
      .map(hexPosToNeighboursWithSide(_).toMap)
      .getOrElse(Map.empty)

  object implicits {
    implicit val hasNeighbours: HasNeighbours[T] =
      (value: T) => neighbours(value)

    implicit val hexHasNeighbourMap: HasNeighbourMap[Side, T] =
      (value: T) => neighbourMap(value)
  }
}

class StoreByHexPosition[T](private[hex] var byPosition: Map[HexPosition, T]) extends StoreableByHexPosition[T] {
}



