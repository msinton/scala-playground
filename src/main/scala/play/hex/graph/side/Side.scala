package play.hex.graph.side

import play.hex.graph
import play.hex.graph.{AntiClockwise, Clockwise, Rotation}
import play.hex.graph.vertex.{E, Vertex, W}

sealed trait Side {
  def clockwise: Side
  def antiClockwise: Side
  def opposite: Side
  def rotate(rotation: Rotation): Side = rotation match {
    case Clockwise => clockwise
    case AntiClockwise => antiClockwise
  }

  def clockwiseVertex: Vertex
  def antiClockwiseVertex: Vertex
  def rotateVertex(rotation: Rotation): Vertex = rotation match {
    case Clockwise => clockwiseVertex
    case AntiClockwise => antiClockwiseVertex
  }
}

case object N extends Side {
  override val clockwise: Side = NE
  override val antiClockwise: Side = NW
  override val opposite: Side = S
  override val antiClockwiseVertex: Vertex = graph.vertex.NW
  override val clockwiseVertex: Vertex = graph.vertex.NE
}
case object NE extends Side {
  override val clockwise: Side = SE
  override val antiClockwise: Side = N
  override val opposite: Side = SW
  override val antiClockwiseVertex: Vertex = graph.vertex.NE
  override val clockwiseVertex: Vertex = E
}
case object SE extends Side {
  override val clockwise: Side = S
  override val antiClockwise: Side = NE
  override val opposite: Side = NW
  override val antiClockwiseVertex: Vertex = graph.vertex.E
  override val clockwiseVertex: Vertex = graph.vertex.SE
}
case object S extends Side {
  override val clockwise: Side = SW
  override val antiClockwise: Side = SE
  override val opposite: Side = N
  override val antiClockwiseVertex: Vertex = graph.vertex.SE
  override val clockwiseVertex: Vertex = graph.vertex.SW
}
case object SW extends Side {
  override val clockwise: Side = NW
  override val antiClockwise: Side = S
  override val opposite: Side = NE
  override val antiClockwiseVertex: Vertex = graph.vertex.SW
  override val clockwiseVertex: Vertex = W
}
case object NW extends Side {
  override val clockwise: Side = N
  override val antiClockwise: Side = SW
  override val opposite: Side = SE
  override val antiClockwiseVertex: Vertex = graph.vertex.W
  override val clockwiseVertex: Vertex = graph.vertex.NW
}
