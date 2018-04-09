package play.river

import play.hex.graph.vertex.{Point, Vertex}
import play.hex.graph.{AntiClockwise, Clockwise, HexPosition, Rotation}
import play.hex.stores.{HexStore, RiverStore}
import play.implicits._

import scala.util.Random

class RiverDiverter(hexes: HexStore, rivers: RiverStore) {

  private def isInland(point: Point) =
    point.hexPositions.forall(hexes.atPos(_).isDefined)

  private def numRiversAt(point: Point) =
    point.edges.count(rivers.atPos(_).isDefined)

  private def edgesWithRiver(hexPosition: HexPosition) =
    hexes.edges(hexPosition).filter(rivers.atPos(_).isDefined)

  def canDivert(point: Point): Boolean =
    numRiversAt(point) == 2 && isInland(point)

  type RotateRoundHex = HexPosition

  /**
    * The new rivers should continue around the hex until they connect with the old rivers
    * or reach the edge of the world.
    */
  private def divertFlowAround(anchor: RotateRoundHex, origin: Point, rotation: Rotation): EdgesWithRivers = {
    val nextPoint: Vertex => Point = anchor.rotate(rotation)

    def loop(fromPoint: Point): EdgesWithRivers = {
      val p = nextPoint(fromPoint.vertex)
      (for {
        edge <- p.edgeBetween(fromPoint)
        if isInland(p) && rivers.atPos(edge).isEmpty && p != origin
        pair = (edge, RiverSegment(Flow(fromPoint, p)))
        rest = loop(p)
      } yield pair +: rest).getOrElse(Nil)
    }

    loop(origin)
  }

  private[river] def divertionData(random: Random, origin: Point): Option[(RotateRoundHex, Rotation)] = for {
    edgeOfFlowingFrom <- random.sample(rivers.edgesWithRiversFlowingFrom(origin))
    flowingToPoint <- edgeOfFlowingFrom.otherPoint(origin)
    rotateRoundHex <- flowingToPoint.hexPositions.intersect(origin.hexPositions).headOption
    rotation = if (origin.vertex.clockwise == flowingToPoint.vertex) AntiClockwise else Clockwise
  } yield (rotateRoundHex, rotation)

  /**
    * From the point, downstream river flows along the remaining side of the hex.
    *
    * It is possible to divert at a source - where there are 2 downstream rivers
    * then randomly choose which one.
    */
  def divert(random: Random, origin: Point): Unit = {
    if (canDivert(origin)) {
      divertionData(random, origin).foreach { case (rotateRoundHex, rotation) =>
        val newRivers = divertFlowAround(rotateRoundHex, origin, rotation)
        // Update store by removing the hexes existing rivers and then adding the new rivers
        edgesWithRiver(rotateRoundHex).foreach(rivers.remove)
        newRivers.foreach((rivers.update _).tupled)
      }
    }
  }

}
