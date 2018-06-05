package play.hex.stores

import org.scalatest.WordSpec
import play.hex._
import play.hex.graph.HexPosition
import play.hex.graph.side.{NE, SW}

class HexStoreTest extends WordSpec {

  "Hex Store" when {

    val clay = Clay()
    val hex1 = Hex(1, clay)
    val pos1 = HexPosition(0, 0)

    "only one hex" when {
      ".neighbours" should {
        val byPos = Map(pos1 -> hex1)

        "be empty" in {

          val hexes = new HexStore(byPos)

          assert(hexes.neighbours(hex1).isEmpty)
        }
      }
    }

    "two neighbouring hexes" should {

      val hex2 = Hex(2, clay)
      val pos2 = HexPosition(0, 1)
      val byPos = Map(pos1 -> hex1, pos2 -> hex2)

      ".neighbours" should {

        "find the other" in {
          val hexes = new HexStore(byPos)

          val neighbours = hexes.neighbours(hex1)
          assert(neighbours.size === 1)
          assert(neighbours == Seq(hex2))

          assert(hexes.neighbours(hex2) == Seq(hex1))
        }
      }

      ".neighbourMap" should {

        "find the other" in {
          val hexes = new HexStore(byPos)

          val neighbours = hexes.neighbourMap(hex1)
          assert(neighbours.size === 1)
          assert(neighbours == Map(NE -> hex2))

          assert(hexes.neighbourMap(hex2) == Map(SW -> hex1))
        }
      }
    }
  }

}