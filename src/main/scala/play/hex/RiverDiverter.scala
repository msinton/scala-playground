package play.hex

import play.hex.stores.{HexStore, RiverStore}
import play.hex.vertex.Point
import play.river.RiverSegment
import play.hex.syntax.NeighbourSyntax._

class RiverDiverter(hexes: HexStore, rivers: RiverStore) {

  import hexes.implicits
//  import rivers.implicits

  private def isInland(point: Point) =
    point.hexPositions.forall(hexes.atPos(_).isDefined)

  private def numRiversAt(point: Point) =
    point.edges.count(rivers.atPos(_).isDefined)

  private def riversAt(hex: Hex) =
    hexes.edges(hex).filter(rivers.atPos(_).isDefined)

  // TODO use the implicit
  private def partitionEdgesByHasRiver(hex: Hex): (Iterable[BordersHex], Iterable[BordersHex]) =
    hexes.edges(hex).partition(rivers.atPos(_).isDefined)

  type RemainingEdges = Iterable[BordersHex]
  type RiverMap = Map[BordersHex, RiverSegment]

  // TODO - continue river flow and divert
  // it should be that all the edges of the hex swap from being river - non-river
  // the tricky part is making sure the flow is correct to
  // might be easiest if can generate sequence of (flowFromPoint, edge)
  // by rotating round the hex - like I did before

  def canDivert(point: Point): Boolean =
    numRiversAt(point) == 2 && isInland(point)

  private def continueRiverFlow(river: RiverSegment, edges: RemainingEdges): RiverMap = ???
  // {
//    val flowFrom = river.flow.to
//    nextEdge(flowFrom, edges)
//      .map(e => (e, RiverSegment(flowFrom, e)))
//      .map{case (e, r) => }
//  }

  /**
    * From the point, downstream river flows along the remaining side of the hex.
    * There is a randomness to how the new branch is created.
    */
  def divert(point: Point): Unit = {
    if (canDivert(point)) {
      for {
        newBranchStartEdge <- point.edges.find(rivers.atPos(_).isEmpty)
        river = RiverSegment(flowFrom = point, newBranchStartEdge)
        hexPos <- point.hexPositions.intersect(river.flow.to.hexPositions).headOption
        hex <- hexes.atPos(hexPos)
        (edgesWithRiver, remainingEdges) = partitionEdgesByHasRiver(hex)
        newRivers = continueRiverFlow(river, remainingEdges)
      } yield {
        edgesWithRiver.foreach(rivers.remove)
        newRivers.foreach { case (e, r) => rivers.update(e, r) }
      }
    }
  }
}
