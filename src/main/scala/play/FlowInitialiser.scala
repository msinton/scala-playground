package play

import com.typesafe.scalalogging.LazyLogging
import play.hex.BordersHex
import play.river.RiverSegment

import scala.util.Random


class FlowInitialiser(random: Random) extends LazyLogging {

  type Point = Int

  def setup(edges: Iterable[BordersHex]): Map[BordersHex, RiverSegment] = {
    setupFlowsFromSources(edges)
    setupFlowsFromRandom(edges)
  }

  // A source is a point where there are 3 rivers connected
  def setupFlowsFromSources(edges: Iterable[BordersHex]): Unit = ???

  private final def setupFlowsFromRandom(edges: Iterable[BordersHex]): Map[BordersHex, RiverSegment] = ???

  private def setRandomFlow(edge: BordersHex): RiverSegment = ???

  private def setFlowFrom(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = ???

  private def setFlowTowards(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = ???

  private final def continueRiverFlow(riversWithFlow: List[RiverSegment]): Unit = ???

  def findSources(rivers: Iterable[RiverSegment]): Set[Point] = ???
}