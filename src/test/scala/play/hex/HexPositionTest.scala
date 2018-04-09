package play.hex

import org.scalatest.WordSpec
import play.hex.graph.side.N

class HexPositionTest extends WordSpec {

  "Hex Position" when {
    "neighbourAt side" should {
      "get the correct neighbour" in {

        val p = HexPosition(0, 0)
        val neighbour = p.neighbourAt(N)

        assert(neighbour.x == -1)
        assert(neighbour.y == 0)
      }
      // TODO more
    }
  }
}
