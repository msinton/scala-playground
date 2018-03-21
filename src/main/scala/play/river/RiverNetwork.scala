package play.river

import com.typesafe.scalalogging.LazyLogging
import play._
import play.hex._

import scala.util.Random


/**
  * Create a network of rivers over the hex grid
  *
  * Goals:
  * - Random network layouts
  * - The layouts should be varied by how they divide the hex grid
  * - The divided grid "hex groups" should be roughly equal in size
  */
class RiverNetwork(random: Random, hexes: HexStore) extends LazyLogging {

  private var _groups: Seq[HexGroup] = Nil

  private def neighboursNotInGroup(hex: Hex, group: Set[Hex]): Set[Hex] = {
    hexes.neighbours(hex).toSet.diff(group)
  }

  private def applyRiversToBorders(hexGroups: Seq[HexGroup]) = {
    val hexBoundaries = for {
      group <- hexGroups.tail
      hex <- group
      boundaryHex <- neighboursNotInGroup(hex, group)
      side <- hex.getSide(boundaryHex)
    } yield (hex, side)

    hexBoundaries.map {
      case (hex, side) => (BordersHex(hexes.toPos(hex).get, side), RiverSegment())
    }.toMap
  }

  def generate(numPlayers: Int): (Map[BordersHex, RiverSegment], Seq[HexGroup]) = {
    val numGroups = if (numPlayers == 1) 2 else numPlayers
    _groups = new HexGridDivider(random).divideIntoRoughlyEqualRandomlyShaped(hexes, numGroups)
    val rivers = applyRiversToBorders(_groups)
    setupFlow(rivers.values.toBuffer)
    (rivers, _groups)
  }

  def groups: Seq[HexGroup] = _groups

  /**
    * Enables the flow to be setup, does nothing if already setup.
    */
  final def setupFlow(rivers: Seq[RiverSegment]): Unit = {
    new FlowInitialiser(random).setup(rivers)
  }

//
//  // TODO refactor - only remove by position else use map from river to position
//  final def removeRiver(river: RiverSegment) = {
//    _riverMap.find(_._2 == river).foreach(x => _riverMap = _riverMap - x._1)
//  }
//
//  final def addRiver(hexA: Hex, sideA: Side): Option[RiverSegment] = {
//    hexes.toPos(hexA).map(p => {
//      val edge = BordersHex(p, sideA)
//      val river = RiverSegment()
//      _riverMap = _riverMap.updated(edge, river)
//      river
//    })
//  }

}
