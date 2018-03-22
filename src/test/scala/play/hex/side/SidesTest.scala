package play.hex.side

import org.scalatest.{GivenWhenThen, WordSpec}

class SidesTest extends WordSpec with GivenWhenThen {

  "Side" when {
    ".clockwise" should {
      "get the correct side" in {

        assert(N.clockwise === NE, "for N")

        assert(NE.clockwise === SE, "for NE")

        assert(SE.clockwise === S, "for SE")

        assert(S.clockwise === SW, "for S")

        assert(SW.clockwise === NW, "for SW")

        assert(NW.clockwise === N, "for NW")
      }
    }

    ".antiClockwise" should {
      "get the correct side" in {

        assert(N.antiClockwise === NW, "for N")

        assert(NE.antiClockwise === N, "for NE")

        assert(SE.antiClockwise === NE, "for SE")

        assert(S.antiClockwise === SE, "for S")

        assert(SW.antiClockwise === S, "for SW")

        assert(NW.antiClockwise === SW, "for NW")
      }
    }
  }
}
