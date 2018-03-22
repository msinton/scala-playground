package play.hex

import org.scalatest.{GivenWhenThen, WordSpec}
import play.hex.side.{N, S}

class SidesTest extends WordSpec with GivenWhenThen {

  "Side" when {
    ".clockwise" should {
      "get the correct side" in {

        assert(N.clockwise === vertices.NE, "for N")

        assert(vertices.NE.clockwise === vertices.SE, "for NE")

        assert(vertices.SE.clockwise === S, "for SE")

        assert(S.clockwise === vertices.SW, "for S")

        assert(vertices.SW.clockwise === vertices.NW, "for SW")

        assert(vertices.NW.clockwise === N, "for NW")
      }
    }

    ".antiClockwise" should {
      "get the correct side" in {

        assert(N.antiClockwise === vertices.NW, "for N")

        assert(vertices.NE.antiClockwise === N, "for NE")

        assert(vertices.SE.antiClockwise === vertices.NE, "for SE")

        assert(S.antiClockwise === vertices.SE, "for S")

        assert(vertices.SW.antiClockwise === S, "for SW")

        assert(vertices.NW.antiClockwise === vertices.SW, "for NW")
      }
    }
  }
}
