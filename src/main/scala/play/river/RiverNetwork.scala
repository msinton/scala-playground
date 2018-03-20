package play.river

import com.typesafe.scalalogging.LazyLogging
import play._
import play.hex.{BoardData, Hex, HexGridDivider, Side}

import scala.util.Random


/**
  * Create a network of rivers over the hex grid
  *
  * Goals:
  * - Random network layouts
  * - The layouts should be varied by how they divide the hex grid
  * - The divided grid "hex groups" should be roughly equal in size
  */
class RiverNetwork(random: Random, boardData: BoardData) extends LazyLogging {

  private var _riverMap = Map.empty[BordersHex, RiverSegment]
  private var _groups: Seq[HexGroup] = Nil

  private def neighboursNotInGroup(hex: Hex, group: Set[Hex]): Set[Hex] = {
    hex.neighbours.values.toSet.diff(group)
  }

  def applyRiversToBorders(hexGroups: Seq[HexGroup]): Seq[RiverSegment] = {
    val hexBoundaries = for {
      group <- hexGroups.tail
      hex <- group
      boundaryHex <- neighboursNotInGroup(hex, group)
      side <- hex.getSide(boundaryHex)
    } yield (hex, side)

    _riverMap = hexBoundaries.map {
      case (hex, side) => (BordersHex(boardData.hexPosition(hex).get, side), RiverSegment())
    }.toMap

    _riverMap.values.toSeq
  }

  def generate(hexes: Seq[Hex], numPlayers: Int): (Seq[RiverSegment], Seq[HexGroup]) = {
    val numGroups = if (numPlayers == 1) 2 else numPlayers
    _groups = new HexGridDivider(random).divideIntoRoughlyEqualRandomlyShaped(hexes.toIndexedSeq, numGroups)
    val rivers = applyRiversToBorders(_groups).toBuffer
    setupFlow(rivers)
    (rivers, _groups)
  }

  def rivers: Iterable[RiverSegment] = _riverMap.values

  def groups: Seq[HexGroup] = _groups

  /**
    * Enables the flow to be setup, does nothing if already setup.
    */
  final def setupFlow(rivers: Seq[RiverSegment]): Unit = {
    val flowInitialiser = new FlowInitialiser(random)
    flowInitialiser.setup(rivers)
  }


  // TODO refactor - only remove by position else use map from river to position
  final def removeRiver(river: RiverSegment) = {
    _riverMap.find(_._2 == river).foreach(x => _riverMap = _riverMap - x._1)
  }

  final def addRiver(hexA: Hex, sideA: Side): Option[RiverSegment] = {
    boardData.hexPosition(hexA).map(p => {
      val edge = BordersHex(p, sideA)
      val river = RiverSegment()
      _riverMap = _riverMap.updated(edge, river)
      river
    })
  }

}

// TODO hexPosition containers
// HexPosition next(side)