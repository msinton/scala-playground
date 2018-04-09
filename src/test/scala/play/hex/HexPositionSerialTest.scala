package play.hex

import org.scalatest.{SequentialNestedSuiteExecution, WordSpec}
import play.hex.graph.HexPosition
import play.hex.graph.side.{N, NE}

class HexPositionSerialTest extends WordSpec {

  "Hex Position" when {
    "creating" should {
      "use cache to prevent GC" in {

        HexPosition.cache = Map.empty

        var set = Set.empty[HexPosition]
        val p = HexPosition(1, 1)
        set += p
        assert(HexPosition.cache.size === set.size)

        set = set ++ p.neighbours
        assert(HexPosition.cache.size === set.size)

        set = set ++ p.neighbourMap.find(_._1 == NE).get._2.neighbours // 2 of the neighbours already created
        assert(HexPosition.cache.size === set.size)
      }
    }
    "neighbourAt side" should {
      "get the correct neighbour" in {

        val p = HexPosition(0, 0)
        val neighbour = p.neighbourAt(N)

        assert(neighbour.x == -1)
        assert(neighbour.y == 0)
      }
    }
  }

}
