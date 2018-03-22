package play.hex

import org.scalatest.{SequentialNestedSuiteExecution, WordSpec}

class HexPositionSerialTest extends WordSpec with SequentialNestedSuiteExecution {

  "Hex Position" when {
    "creating" should {
      "use cache to prevent GC" in {

        HexPosition.cache = Map.empty

        var set = Set.empty[HexPosition]
        val p1 = HexPosition(1, 1)
        set += p1
        assert(HexPosition.cache.size === set.size)

        set = set ++ p1.neighbours
        assert(HexPosition.cache.size === set.size)

        set = set ++ p1.neighbourMap.find(_._1 == NE).get._2.neighbours // 2 of the neighbours already created
        assert(HexPosition.cache.size === set.size)
      }
    }
  }

}
