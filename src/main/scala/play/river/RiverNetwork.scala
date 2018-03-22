package play.river

import com.typesafe.scalalogging.LazyLogging
import play._
import play.hex._
import play.hex.stores.HexStore

import scala.util.Random
import play.hex.syntax.NeighbourSyntax._


/**
  * Create a network of rivers over the hex grid
  *
  * Goals:
  * - Random network layouts
  * - The layouts should be varied by how they divide the hex grid
  * - The divided grid "hex groups" should be roughly equal in size
  */
class RiverNetwork(random: Random, hexes: HexStore) extends LazyLogging {

  import hexes.implicits._

  private var _groups: Seq[HexGroup] = Nil

  private def neighboursNotInGroup(hex: Hex, group: Set[Hex]): Set[Hex] = {
    hex.neighbours.toSet.diff(group)
  }

  private def applyRiversToBorders(hexGroups: Seq[HexGroup]) = {
    val hexBoundaries = for {
      group <- hexGroups.tail
      hex <- group
      boundaryHex <- neighboursNotInGroup(hex, group)
      side <- hexes.getSide(hex, boundaryHex)
    } yield (hex, side)

    hexBoundaries.map {
      case (hex, side) => BordersHex(hexes.toPos(hex).get, side)
    }.toMap
  }

  def generate(numPlayers: Int): (Map[BordersHex, RiverSegment], Seq[HexGroup]) = {
    val numGroups = if (numPlayers == 1) 2 else numPlayers
    _groups = new HexGridDivider(random).divideIntoRoughlyEqualRandomlyShaped(hexes, numGroups)
    val edges = applyRiversToBorders(_groups)
    val rivers = setupFlow(edges.values.toBuffer)
    (rivers, _groups)
  }

  def groups: Seq[HexGroup] = _groups

  /**
    * Enables the flow to be setup, does nothing if already setup.
    */
  final def setupFlow(edges: Seq[BordersHex]): Map[BordersHex, RiverSegment] = {
    new FlowInitialiser(random).setup(edges)
  }

}
