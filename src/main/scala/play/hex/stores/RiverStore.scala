package play.hex.stores

import play.hex.{BordersHex, Hex}
import play.hex.vertices.Point
import play.river.RiverSegment

class RiverStore(private[hex] var byPosition: Map[BordersHex, RiverSegment]) extends StoreableByEdge[RiverSegment] {

  def neighbours(inflowDirection: Boolean, river: RiverSegment): Iterable[(RiverSegment, Point)] = {
    toPos(river).map(edge => {
      val sourceOfNeighbours = if (inflowDirection) river.flow.to else river.flow.from
      val nextSideOnThis = if (inflowDirection && river.flow.to.) edge.side.antiClockwise
      at(BordersHex(edge.hexPosition, sideOfThisHexPositionToLookAt))

      edge.hexPosition.
      sourceOfNeighbours.
    }).getOrElse(Iterable.empty)
  }


  /**
    * @return True if river flows towards the hex i.e. the "to" point lies on the hex.
    */
  def flowsTowards(river: RiverSegment, hex: Hex): Boolean = {
    hex.getVertex(river.flow.to).isDefined
  }

  /**
    * @return True if this flows towards the otherRiver
    */
  def flowsTowards(river: RiverSegment, otherRiver: RiverSegment): Boolean = {
    otherRiver.otherPoint(river.flow.to).isDefined
  }
}


/**
  * Gets the neighbouring river segments at either the rivers 'start' point or its 'end' point.
  * There may be 2 since it is possible for 3 rivers to meet at a point.
  *
  * @param inflowDirection If true then returns the segment(s) which are joined to the <code>from</code> point, or <i>in flow</i> direction of this river.
  *                        Otherwise the segments at the <i>out flow</i> direction are returned, along with their respective <code>from</code> points.
  * @return Map of riverSegment to point
  */
//def getNeighbour(inflowDirection: Boolean): Iterable[(RiverSegment, Point)] = {
//
//  def findNeighbourAtPoint(hex: Hex, side: Side, point: Point): Option[(RiverSegment, Point)] = {
//
//  val isPointOnClockwiseVertexOfSide = hex.getVertex(point).contains(side.clockwiseVertex)
//  // we want to look for rivers that are on the next side of the hex, continuing in the same direction
//  if (isPointOnClockwiseVertexOfSide)
//  hex.rivers.get(side.clockwise) map {(_, point)}
//  else
//  hex.rivers.get(side.anticlockwise) map {(_, point)}
//}
//
//  val hexANeighbour = for {
//  flw <- flow
//  point = if (inflowDirection) flw.to else flw.from
//  n <- findNeighbourAtPoint(hexA, sideA, point)
//} yield n
//
//  val hexBNeighbour = for {
//  flw <- flow
//  point = if (inflowDirection) flw.to else flw.from
//  hex <- hexB
//  side <- sideB
//  n <- findNeighbourAtPoint(hex, side, point)
//} yield n
//
//  hexANeighbour ++ hexBNeighbour
//}