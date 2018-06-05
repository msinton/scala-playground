package play.hex

import org.scalatest.WordSpec
import play.hex.graph.HexPosition
import play.hex.graph.side._

class HexPositionTest extends WordSpec {

  "Hex Position" when {
    "neighbourAt side" should {
      "get the correct neighbour" in {

        val p = HexPosition(0, 0)
        val neighbour = p.neighbourAt(N)

        assert(neighbour.x == -1)
        assert(neighbour.y == 0)
      }
    }

    "position in EVEN column" when {

      val p = HexPosition(0, 0)

      "neighbour map of hex" should {
        "have neighbours in the correct positions and locations" in {

          val neighbours = p.neighbourMap

          assert(neighbours.size == 6)
          assert(neighbours == Map(
            N -> HexPosition(-1, 0),
            NW -> HexPosition(0, -1),
            NE -> HexPosition(0, 1),
            S -> HexPosition(1, 0),
            SW -> HexPosition(1, -1),
            SE -> HexPosition(1, 1)
          ))
        }
      }

      "neighbours of hex" should {
        "have the same neighbours as neighbourMap" in {

          val neighbours = p.neighbours
          assert(neighbours.toSet == p.neighbourMap.values.toSet)
        }
      }
    }

    "position in ODD column" when {

      val p = HexPosition(0, 1)

      "neighbour map of hex" should {
        "have neighbours in the correct positions and locations" in {

          val neighbours = p.neighbourMap

          assert(neighbours.size == 6)
          assert(neighbours == Map(
            N -> HexPosition(-1, 1),
            NE -> HexPosition(-1, 2),
            NW -> HexPosition(-1, 0),
            SW -> HexPosition(0, 0),
            SE -> HexPosition(0, 2),
            S -> HexPosition(1, 1)
          ))
        }
      }

      "neighbours of hex" should {
        "have the same neighbours as neighbourMap" in {

          val neighbours = p.neighbours
          assert(neighbours.toSet == p.neighbourMap.values.toSet)
        }
      }
    }
  }
}
