package play.hex.graph.vertex

trait Vertex {
  def clockwise: Vertex
  def antiClockwise: Vertex
}

case object NW extends Vertex {
  override val clockwise: Vertex = NE
  override val antiClockwise: Vertex = W
}
case object NE extends Vertex {
  override val clockwise: Vertex = E
  override val antiClockwise: Vertex = NW
}
case object E extends Vertex {
  override val clockwise: Vertex = SE
  override val antiClockwise: Vertex = NE
}
case object SE extends Vertex {
  override val clockwise: Vertex = SW
  override val antiClockwise: Vertex = E
}
case object SW extends Vertex {
  override val clockwise: Vertex = W
  override val antiClockwise: Vertex = SE
}
case object W extends Vertex {
  override val clockwise: Vertex = NW
  override val antiClockwise: Vertex = SW
}
