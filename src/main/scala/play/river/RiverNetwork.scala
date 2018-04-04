package play.river

import com.typesafe.scalalogging.LazyLogging
import play.hex._
import play.hex.stores.HexStore
import play.hex.syntax.NeighbourSyntax._

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
      side <- hex.sideBetween(boundaryHex)
    } yield (hex, side)

    hexBoundaries.map({
      case (hex, side) => BordersHex(hexes.toPos(hex).get, side)
    })
  }

  def generate(numPlayers: Int): (Map[BordersHex, RiverSegment], Seq[HexGroup]) = {
    val numGroups = if (numPlayers == 1) 2 else numPlayers
    _groups = new HexGridDivider(random).divideIntoRoughlyEqualRandomlyShaped(hexes, numGroups)
    val edges = applyRiversToBorders(_groups)
    val rivers = setupFlow(edges.toBuffer)
    (rivers, _groups)
  }

  def groups: Seq[HexGroup] = _groups

  final def setupFlow(edges: Seq[BordersHex]): Map[BordersHex, RiverSegment] = {
    new FlowInitialiser(random).setup(edges)
  }

}
