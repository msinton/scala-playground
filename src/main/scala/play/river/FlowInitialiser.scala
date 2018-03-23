package play.river

import com.typesafe.scalalogging.LazyLogging
import play.hex.BordersHex
import play.hex.vertex.Point
import play.utils.Utils

import scala.util.Random


class FlowInitialiser(random: Random) extends LazyLogging {

  type RemainingEdgesIndexed = IndexedSeq[BordersHex]
  type RemainingEdges = Iterable[BordersHex]
  type RiverMap = Map[BordersHex, RiverSegment]

  def setup(edges: IndexedSeq[BordersHex]): RiverMap = {
    val (remainingEdges, rivers) = setupFlowsFromSources(edges)
    rivers ++ setupFlowsFromRandom(remainingEdges.toIndexedSeq)
  }

  // A source is a point where there are 3 rivers connected
  def setupFlowsFromSources(edges: RemainingEdgesIndexed): (RemainingEdges, RiverMap) = {
    val rivers = findSources(edges)
      .flatMap(point => point.edges.map(edge => (edge, RiverSegment(Flow(point, edge.otherPoint(point))))))
      .toMap

    logger.debug(s"Got sources: $rivers")
    continueRiverFlow((edges.toSet -- rivers.keySet).toIndexedSeq, rivers)
  }

  private final def setupFlowsFromRandom(edges: RemainingEdgesIndexed, rivers: RiverMap = Map.empty): Map[BordersHex, RiverSegment] = {
    Utils.sample(edges, random)
      .map({edge =>
        val river = setRandomFlow(edge)
        val pivotPoint = river.flow.from
        // get neighbours of pivotPoint and for each set flow
        val candidateEdges = (river.flow.from.edges - edge).intersect(edges.toSet)
        val nextRivers = candidateEdges
          .map(e => (e, RiverSegment(Flow(pivotPoint, e.otherPoint(pivotPoint)))))
          .toMap + (edge -> river)

        val (newEdges, newRivers) = continueRiverFlow(edges.toSet -- nextRivers.keySet, rivers ++ nextRivers)
        setupFlowsFromRandom(newEdges.toIndexedSeq, newRivers)
    }).getOrElse(rivers)
  }

  private def setRandomFlow(edge: BordersHex): RiverSegment = {
    val Seq(from, to) = if (random.nextBoolean()) edge.points.toSeq else edge.points.toSeq.reverse
    RiverSegment(Flow(from, to))
  }

  private def setFlowFrom(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = ???

  private def setFlowTowards(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = ???

  private final def continueRiverFlow(edges: RemainingEdges, rivers: RiverMap): (RemainingEdges, RiverMap) = {
    // TODO make more readable with convenience method to create River
    val nextRivers = rivers.flatMap({
      case (_, river) =>
        lazy val fromPoint = river.flow.to
        fromPoint.edges.intersect(edges.toSet)
          .map(e => (e, RiverSegment(Flow(fromPoint, e.otherPoint(river.flow.to)))))
    })

    if (nextRivers.isEmpty) (edges, rivers)
    else continueRiverFlow(edges.toSet -- nextRivers.keySet, rivers ++ nextRivers)
  }

  def findSources(edges: Iterable[BordersHex]): Set[Point] = {
      edges.flatMap(e => Seq(
        Point(e.hexPosition, e.side.clockwiseVertex),
        Point(e.hexPosition, e.side.antiClockwiseVertex)
      )).groupBy(identity)
      .filter(_._2.size > 2)
      .keySet
  }
}

/*

class FlowInitialiser(random: Random) extends LazyLogging {

  def setup(rivers: Iterable[RiverSegment]): Unit = {
    setupFlowsFromSources(rivers)
    setupFlowsFromRandom(rivers)
  }

  // A source is a point where there are 3 rivers connected
  def setupFlowsFromSources(rivers: Iterable[RiverSegment]): Unit = {

    val riversWithFlow = findSources(rivers)
      .flatMap(point => point.getRivers.map(setFlowFrom(_, point)))

    logger.debug(s"Got sources: $riversWithFlow")
    continueRiverFlow(riversWithFlow.toList)
  }

  @tailrec
  private final def setupFlowsFromRandom(rivers: Iterable[RiverSegment]): Unit = {

    rivers.filter(r => r.flow.isEmpty) match {
      case Nil =>

      case riversWithoutFlow =>

        val randomRiver = Utils.getRandom(riversWithoutFlow, random)
        logger.debug(s"Setting random flow $randomRiver")

        val next =
          setRandomFlow(randomRiver).getNeighbour(inflowDirection = true)
            .map(x => setFlowFrom(x._1, randomRiver.flow.get.to)) ++
            randomRiver.getNeighbour(inflowDirection = false)
              .map(y => setFlowTowards(y._1, randomRiver.flow.get.from))

        logger.debug(s"Random flow next: $next")
        continueRiverFlow(next.toList)

        setupFlowsFromRandom(riversWithoutFlow)
    }
  }

  private def setRandomFlow(river: RiverSegment): RiverSegment = {
    val vertex = if (random.nextBoolean()) river.sideA.clockwiseVertex else river.sideA.anticlockwiseVertex
    river.setFlowUsingFrom(river.hexA.vertices(vertex))
    river
  }

  private def setFlowFrom(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = {
    riverSegment.setFlowUsingFrom(fromPoint)
    riverSegment
  }

  private def setFlowTowards(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = {
    riverSegment.setFlowUsingFrom(fromPoint)
    riverSegment
  }

  @tailrec
  private final def continueRiverFlow(riversWithFlow: List[RiverSegment]): Unit = {

    riversWithFlow match {
      case Nil => logger.debug("<--- continue river flow finished")
      case _ => continueRiverFlow(riversWithFlow.flatMap(r => {
        val point = r.flow.get.to
        point.getRivers.filter(_.flow.isEmpty).map(setFlowFrom(_, point))
      }))
    }
  }

  def findSources(rivers: Iterable[RiverSegment]): Set[Point] = {
    rivers
      .flatMap(r => Seq(
        r.hexA.vertices(r.sideA.anticlockwiseVertex()),
        r.hexA.vertices(r.sideA.clockwiseVertex())))
      .groupBy(p => p)
      .filter(_._2.size == 3)
      .keySet
  }
}

 */