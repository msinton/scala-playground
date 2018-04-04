package play.river

import com.typesafe.scalalogging.LazyLogging
import play.hex.BordersHex
import play.hex.vertex.Point
import play.utils.Utils

import scala.annotation.tailrec
import scala.util.Random
import play.implicits._


class FlowInitialiser(random: Random) extends LazyLogging {

  type RemainingEdges = Iterable[BordersHex]
  type RiverMap = Map[BordersHex, RiverSegment]

  final def setup(edges: RemainingEdges): RiverMap = {
    val (remainingEdges, rivers) = createFlowsFromSources(edges)
    rivers ++ createRiversFromRandom(remainingEdges)
  }

  // A source is a point where there are 3 rivers connected
  final def createFlowsFromSources(edges: RemainingEdges): (RemainingEdges, RiverMap) = {
    val rivers = findSources(edges)
      .flatMap(point => point.edges.map(edge => (edge, RiverSegment(point, edge))))
      .toMap

    logger.debug(s"Got sources: $rivers")
    continueRiverFlow(edges.toSet -- rivers.keySet, rivers)
  }

  @tailrec
  private final def createRiversFromRandom(edges: RemainingEdges, rivers: RiverMap = Map.empty): RiverMap = {
    random.sample(edges.toIndexedSeq) match {
      case Some(edge) =>
        val river = setRandomFlow(edge)
        val pivotPoint = river.flow.from
        // get edges of pivotPoint without rivers and for each set flow
        val nextRivers = remainingEdgesOf(pivotPoint, edges.toSet - edge)
          .map(e => (e, RiverSegment(flowFrom = pivotPoint, e)))
          .toMap + (edge -> river)

        val (newEdges, newRivers) = continueRiverFlow(edges.toSet -- nextRivers.keySet, rivers ++ nextRivers)
        createRiversFromRandom(newEdges, newRivers)

      case _ => rivers
    }
  }

  private final def setRandomFlow(edge: BordersHex): RiverSegment = {
    val Seq(from, to) = if (random.nextBoolean()) edge.points.toSeq else edge.points.toSeq.reverse
    RiverSegment(Flow(from, to))
  }

  private final def remainingEdgesOf(point: Point, edges: RemainingEdges): Set[BordersHex] =
    point.edges.intersect(edges.toSet)

  @tailrec
  private final def continueRiverFlow(edges: RemainingEdges, rivers: RiverMap): (RemainingEdges, RiverMap) = {
    val nextRivers = rivers.flatMap({
      case (_, river) =>
        val flowFrom = river.flow.to
        remainingEdgesOf(flowFrom, edges)
          .map(e => (e, RiverSegment(flowFrom, e)))
    })

    if (nextRivers.isEmpty) (edges, rivers)
    else continueRiverFlow(edges.toSet -- nextRivers.keySet, rivers ++ nextRivers)
  }

  private[river] final def findSources(edges: RemainingEdges): Set[Point] = {
    edges.flatMap(_.points)
      .groupBy(identity)
      .filter(_._2.size > 2)
      .keySet
  }
}
