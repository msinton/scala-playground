package play.hex.vertex

import org.scalatest.WordSpec
import play.hex.graph.side.S
import play.hex.graph.vertex._
import play.hex.{BordersHex, graph, side}

class PointTest extends WordSpec {

  "Point create" when {
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
        assert(p.hexPosition == HexPosition(1, -1))
        assert(p.vertex == E)
      }
    }
    "ask for point on W" should {
      val p = Point(hex, W)

      "supply the normalized point" in {
        assert(p.hexPosition == HexPosition(0, -1))
        assert(p.vertex == SE)
      }
    }
    "ask for point on NW" should {
      val p = Point(hex, NW)

      "supply the normalized point" in {
        assert(p.hexPosition == HexPosition(0, -1))
        assert(p.vertex == E)
      }
    }
    "ask for point on NE" should {
      val p = Point(hex, NE)

      "supply the normalized point" in {
        assert(p.hexPosition == HexPosition(-1, 0))
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

  "Point edges" when {
    val hex = HexPosition(0, 0)

    "configuration 1, East vertex" should {
      val p = Point(hex, E)
      val edges = p.edges

      "have edges that surround the point" in {
        assert(edges.contains(BordersHex(hex, graph.side.SE)))
        assert(edges.contains(BordersHex(hex, graph.side.NE)))
        assert(edges.contains(BordersHex(HexPosition(0, 1), S)))
      }
      "have edges in common with surrounding points" in {
        val pointAbove = Point(hex, NE)
        val pointBelow = Point(hex, SE)
        val pointRight = Point(HexPosition(0, 1), SE)

        assert(edges.union(pointAbove.edges).size === 5)
        assert(edges.union(pointBelow.edges).size === 5)
        assert(edges.union(pointRight.edges).size === 5)
      }
    }

    "configuration 2, SE vertex" should {
      val p = Point(hex, SE)
      val edges = p.edges

      "have edges that surround the point" in {
        assert(edges.contains(BordersHex(hex, graph.side.SE)))
        assert(edges.contains(BordersHex(hex, graph.side.S)))
        assert(edges.contains(BordersHex(HexPosition(1, 0), graph.side.NE)))
      }
      "have edges in common with surrounding points" in {
        val pointAbove = Point(hex, E)
        val pointBelow = Point(HexPosition(1, 0), E)
        val pointRight = Point(hex, SW)

        assert(edges.union(pointAbove.edges).size === 5)
        assert(edges.union(pointBelow.edges).size === 5)
        assert(edges.union(pointRight.edges).size === 5)
      }
    }
  }

}
