package play.river

import org.scalatest.{GivenWhenThen, WordSpec}
import play.hex.graph.side.{NE, S, SE}
import play.hex.side._
import play.hex.BordersHex

import scala.util.Random

class FlowInitialiserTest extends WordSpec with GivenWhenThen {

  "River Setup" when {

    "there are a small number of edges in a single line" should {
      val edges = Seq(
        BordersHex(HexPosition(0, 0), SE),
        BordersHex(HexPosition(1, 0), NE),
        BordersHex(HexPosition(1, 0), SE),
        BordersHex(HexPosition(2, 0), NE)
      )

      "create a river that flows in at most 2 directions" in {

        (1 to 10).map { testIndex =>

          Given(s"Test index: $testIndex")
          val riverMap = new FlowInitialiser(new Random(testIndex)).setup(edges)

          assert(riverMap.size == edges.size)
          assert(riverMap.keySet === edges.toSet)
          val riverStarts = riverMap.values.groupBy(_.flow.from)

          Then("There can be one point that two rivers flow from")
          val sources = riverStarts.filter(_._2.size >= 2)
          assert(sources.size <= 1)

          Then("The rest of the rivers flow one to the next, so do not share the same point")
          assert(riverStarts.count(_._2.size == 1) >= edges.size - 2)

          Then("all points of the edges are either a from or to of a rivers flow")
          assert(edges.flatMap(_.points).toSet
            .forall(point => riverMap.values.exists(river => river.flow.to == point || river.flow.from == point)))
        }
      }
    }

    "the edges form two lines that cross with one overlap" should {
      /**
        *   0,0/
        *      \ 1,1
        *        ---
        *   1,0/
        *   ---
        *      \ 2,1
        *   2,0
        */
      val edges = Seq(
        BordersHex(HexPosition(0, 0), SE),
        BordersHex(HexPosition(1, 0), NE),
        BordersHex(HexPosition(1, 0), SE),
        BordersHex(HexPosition(2, 0), NE),
        // second line
        BordersHex(HexPosition(1, 0), S),
        BordersHex(HexPosition(1, 1), S)
      )

      "create two rivers that cross" in {

        (1 to 10).map { testIndex =>

          Given(s"Test index: $testIndex")
          val riverMap = new FlowInitialiser(new Random(testIndex)).setup(edges)

          assert(riverMap.size == edges.size)
          assert(riverMap.keySet === edges.toSet)
          val riverStarts = riverMap.values.groupBy(_.flow.from)

          Then("There must be at least 1 point that 3 rivers flow from")
          val triSources = riverStarts.filter(_._2.size == 3)
          assert(triSources.nonEmpty)

          Then("The is 1 river that does not share same 'from' point as another")
          assert(riverStarts.count(_._2.size == 1) === 1)

          Then("all points of the edges are either a from or to of a rivers flow")
          assert(edges.flatMap(_.points).toSet
            .forall(point => riverMap.values.exists(river => river.flow.to == point || river.flow.from == point)))
        }
      }
    }
  }
}
