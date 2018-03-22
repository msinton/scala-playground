package play.hex.vertices

import org.scalatest.WordSpec
import play.hex.HexPosition

class PointTest extends WordSpec {

  "Point" when {
    val hex = HexPosition(0, 0)

    "ask for point on SE" should {
      val p = Point(hex, SE)

      "supply the same point" in {
        assert(p.hexPosition == hex)
        assert(p.vertex == SE)
      }
    }
    "ask for point on SW" should {
      val p = Point(hex, SW)

      "supply the normalized point" in {
        assert(p.hexPosition == HexPosition(-1, -1))
        assert(p.vertex == E)
      }
    }
    "ask for point on W" should {
      val p = Point(hex, W)

      "supply the normalized point" in {
        assert(p.hexPosition == HexPosition(-1, 0))
        assert(p.vertex == SE)
      }
    }
    "ask for point on NW" should {
      val p = Point(hex, NW)

      "supply the normalized point" in {
        assert(p.hexPosition == HexPosition(-1, 0))
        assert(p.vertex == E)
      }
    }
    "ask for point on NE" should {
      val p = Point(hex, NW)

      "supply the normalized point" in {
        assert(p.hexPosition == HexPosition(0, -1))
        assert(p.vertex == SE)
      }
    }
    "ask for point on E" should {
      val p = Point(hex, E)

      "supply the same point" in {
        assert(p.hexPosition == hex)
        assert(p.vertex == E)
      }
    }
  }

}
