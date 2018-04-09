package play.hex.graph.syntax

import play.hex.graph.Rotation

trait Rotates[A, From, To] {
  def rotate(anchor: A, rotation: Rotation): From => To
}
