package play.hex.stores

import play.hex._
import play.hex.graph.HexPosition
import play.hex.graph.side.Side
import play.hex.syntax.{HasEdges, HasNeighbourMap, HasNeighbours, HasSides}

trait StorableByHexPosition[T] extends StorableByPosition[HexPosition, T] {
  def at(x: Int, y: Int): Option[T] = byPosition.get(HexPosition(x, y))

  private def findNeighbourPosition(p: HexPosition, neighbourHex: T): Option[(Side, HexPosition)] =
    p.neighbourMap.find({
      case (_, neighbourP) => atPos(neighbourP).contains(neighbourHex)
    })

  private def hexPosToNeighbours(hexPos: HexPosition) =
    for {
      pos <- hexPos.neighbours
      t <- atPos(pos)
    } yield t

  private def hexPosToNeighboursWithSide(hexPos: HexPosition) =
    for {
      (side, pos) <- hexPos.neighbourMap
      t <- atPos(pos)
    } yield (side, t)

  def neighbours(t: T): Iterable[T] =
    toPos(t)
      .map(hexPosToNeighbours)
      .getOrElse(Nil)

  def neighbourMap(t: T): Map[Side, T] =
    toPos(t)
      .map(hexPosToNeighboursWithSide(_).toMap)
      .getOrElse(Map.empty)

  def sideBetween(t: T, neighbour: T): Option[Side] =
    for {
      p <- toPos(t)
      (side, _) <- findNeighbourPosition(p, neighbour)
    } yield side

  def edges(t: T): Iterable[BordersHex] = {
    toPos(t).map { hexPos =>
      neighbours(t)
        .map(sideBetween(t, _))
        .collect {
          case Some(side) => BordersHex(hexPos, side)
        }
    }.getOrElse(Nil)
  }

  def edges(p: HexPosition): Iterable[BordersHex] =
    atPos(p).map(edges).getOrElse(Nil)

  object implicits {
    implicit val hasNeighbours: HasNeighbours[T] =
      (value: T) => neighbours(value)

    implicit val hexHasNeighbourMap: HasNeighbourMap[Side, T] =
      (value: T) => neighbourMap(value)

    implicit val hasSides: HasSides[T] =
      (value: T, neighbour: T) => sideBetween(value, neighbour)
  }
}


class StoreByHexPosition[T](private[hex] var byPosition: Map[HexPosition, T]) extends StorableByHexPosition[T] {
}



