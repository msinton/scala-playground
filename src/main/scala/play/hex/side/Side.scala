package play.hex.side

import play.hex.vertex
import play.hex.vertex.Vertex

sealed trait Side {
  def clockwise: Side
  def antiClockwise: Side
  def opposite: Side

  def clockwiseVertex: Vertex
  def antiClockwiseVertex: Vertex
}

case object N extends Side {
  override val clockwise: Side = NE
  override val antiClockwise: Side = NW
  override val opposite: Side = S
  override val antiClockwiseVertex: Vertex = vertex.NW
  override val clockwiseVertex: Vertex = vertex.NE
}
case object NE extends Side {
  override val clockwise: Side = SE
  override val antiClockwise: Side = N
  override val opposite: Side = SW
  override val antiClockwiseVertex: Vertex = vertex.NE
  override val clockwiseVertex: Vertex = vertex.E
}
case object SE extends Side {
  override val clockwise: Side = S
  override val antiClockwise: Side = NE
  override val opposite: Side = NW
  override val antiClockwiseVertex: Vertex = vertex.E
  override val clockwiseVertex: Vertex = vertex.SE
}
case object S extends Side {
  override val clockwise: Side = SW
  override val antiClockwise: Side = SE
  override val opposite: Side = N
  override val antiClockwiseVertex: Vertex = vertex.SE
  override val clockwiseVertex: Vertex = vertex.SW
}
case object SW extends Side {
  override val clockwise: Side = NW
  override val antiClockwise: Side = S
  override val opposite: Side = NE
  override val antiClockwiseVertex: Vertex = vertex.SW
  override val clockwiseVertex: Vertex = vertex.W
}
case object NW extends Side {
  override val clockwise: Side = N
  override val antiClockwise: Side = SW
  override val opposite: Side = SE
  override val antiClockwiseVertex: Vertex = vertex.W
  override val clockwiseVertex: Vertex = vertex.NW
}
