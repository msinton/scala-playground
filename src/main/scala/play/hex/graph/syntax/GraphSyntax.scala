package play.hex.graph.syntax

import play.hex.graph.Rotation

trait GraphSyntax {

  implicit class GraphRotateOps[A, From, To](anchor: A) {

    def rotate(rotation: Rotation)(implicit r: Rotates[A, From, To]): From => To =
      r.rotate(anchor, rotation)
  }
}
