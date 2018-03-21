package play.hex

import org.scalatest.WordSpec

class HexPositionTest extends WordSpec {

  "Hex Position" when {
    "creating" should {
      "use cache to prevent GC" in {

        HexPosition.cache.clear()

        var set = Set.empty[HexPosition]
        val p1 = HexPosition(1, 1)
        set += p1
        assert(HexPosition.cache.size === set.size)

        set = set ++ p1.neighbours
        assert(HexPosition.cache.size === set.size)

        set = set ++ p1.neighboursWithSides.find(_._1 == NE).get._2.neighbours // 2 of the neighbours already created
        assert(HexPosition.cache.size === set.size)
      }
    }
  }

}
