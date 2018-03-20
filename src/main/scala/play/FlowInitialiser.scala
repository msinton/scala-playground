package play

import com.typesafe.scalalogging.LazyLogging
import play.river.RiverSegment

import scala.util.Random


class FlowInitialiser(random: Random) extends LazyLogging {

  type Point = Int

  def setup(rivers: Iterable[RiverSegment]): Unit = {
    setupFlowsFromSources(rivers)
    setupFlowsFromRandom(rivers)
  }

  // A source is a point where there are 3 rivers connected
  def setupFlowsFromSources(rivers: Iterable[RiverSegment]): Unit = ???

  private final def setupFlowsFromRandom(rivers: Iterable[RiverSegment]): Unit = ???

  private def setRandomFlow(river: RiverSegment): RiverSegment = ???

  private def setFlowFrom(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = ???

  private def setFlowTowards(riverSegment: RiverSegment, fromPoint: Point): RiverSegment = ???

  private final def continueRiverFlow(riversWithFlow: List[RiverSegment]): Unit = ???

  def findSources(rivers: Iterable[RiverSegment]): Set[Point] = ???
}