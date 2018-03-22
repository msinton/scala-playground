package play.hex

import org.scalatest.{GivenWhenThen, WordSpec}
import play.hex.side.{N, S, Side}

class BordersHexTest extends WordSpec with GivenWhenThen {

  def create(x: Int, y: Int, s: Side) = BordersHex(HexPosition(x, y), s)

  "creating BordersHex" when {
    "position is in even column" when {
      val y = 0
      "side is N" should {
        val side = N
        "translate hex position and side" in {

          val bordersHex = create(0, y, side)

          Then("side is translated")
          assert(bordersHex.side === S)

          And("position is translated")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === -1)
        }
      }

      "side is NE" should {
        val side = vertices.NE
        "translate hex position and side" in {

          val bordersHex = create(0, y, side)

          Then("side is translated")
          assert(bordersHex.side === vertices.SW)

          And("position is translated")
          assert(bordersHex.hexPosition.y === y + 1)
          assert(bordersHex.hexPosition.x === 0)
        }
      }

      "side is NW" should {
        val side = vertices.NW
        "translate hex position and side" in {

          val bordersHex = create(0, y, side)

          Then("side is translated")
          assert(bordersHex.side === vertices.SE)

          And("position is translated")
          assert(bordersHex.hexPosition.y === y - 1)
          assert(bordersHex.hexPosition.x === 0)
        }
      }

      "side is S" should {
        val side = S
        "NOT translate hex position nor side" in {

          val bordersHex = create(0, y, side)

          Then("side unchanged")
          assert(bordersHex.side === side)

          And("position unchanged")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === 0)
        }
      }

      "side is SW" should {
        val side = vertices.SW
        "NOT translate hex position nor side" in {

          val bordersHex = create(0, y, side)

          Then("side unchanged")
          assert(bordersHex.side === side)

          And("position unchanged")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === 0)
        }
      }

      "side is SE" should {
        val side = vertices.SE
        "NOT translate hex position nor side" in {

          val bordersHex = create(0, y, side)

          Then("side unchanged")
          assert(bordersHex.side === side)

          And("position unchanged")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === 0)
        }
      }
    }

    "position is in odd column" when {
      val y = 1
      "side is N" should {
        val side = N
        "translate hex position and side" in {

          val bordersHex = create(0, y, side)

          Then("side is translated")
          assert(bordersHex.side === S)

          And("position is translated")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === -1)
        }
      }

      "side is NE" should {
        val side = vertices.NE
        "translate hex position and side" in {

          val bordersHex = create(0, y, side)

          Then("side is translated")
          assert(bordersHex.side === vertices.SW)

          And("position is translated")
          assert(bordersHex.hexPosition.y === y + 1)
          assert(bordersHex.hexPosition.x === -1)
        }
      }

      "side is NW" should {
        val side = vertices.NW
        "translate hex position and side" in {

          val bordersHex = create(0, y, side)

          Then("side is translated")
          assert(bordersHex.side === vertices.SE)

          And("position is translated")
          assert(bordersHex.hexPosition.y === y - 1)
          assert(bordersHex.hexPosition.x === -1)
        }
      }

      "side is S" should {
        val side = S
        "NOT translate hex position nor side" in {

          val bordersHex = create(0, y, side)

          Then("side unchanged")
          assert(bordersHex.side === side)

          And("position unchanged")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === 0)
        }
      }

      "side is SW" should {
        val side = vertices.SW
        "NOT translate hex position nor side" in {

          val bordersHex = create(0, y, side)

          Then("side unchanged")
          assert(bordersHex.side === side)

          And("position unchanged")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === 0)
        }
      }

      "side is SE" should {
        val side = vertices.SE
        "NOT translate hex position nor side" in {

          val bordersHex = create(0, y, side)

          Then("side unchanged")
          assert(bordersHex.side === side)

          And("position unchanged")
          assert(bordersHex.hexPosition.y === y)
          assert(bordersHex.hexPosition.x === 0)
        }
      }
    }
  }
}
