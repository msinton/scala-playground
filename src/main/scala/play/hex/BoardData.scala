package play.hex

import play.edge.{Boat, Bridge, Wall}
import play.hex.graph.HexPosition
import play.hex.stores._
import play.river.{RiverNetwork, RiverSegment}

import scala.util.Random

case class Person()

class BoardData(numPlayers: Int) {
  
  private def setupHexes: Iterable[(HexPosition, Hex)] = ???
//  val typeWeightings = TypeWeightings() - in setup

  
  val hexes: HexStore = new HexStore(setupHexes.toMap)

  val people: StorableByHexPosition[Person] = new StoreByHexPosition(Map.empty[HexPosition, Person])
  val walls: StorableByHexPosition[Wall] = new StoreByHexPosition(Map.empty[HexPosition, Wall])

  val random = new Random()

  // TODO think about groups - what I will do with them beyond testing
  val rivers: StorableByEdge[RiverSegment] = {
    val (rs, _) = new RiverNetwork(random, hexes).generate(numPlayers)
    new StoreByEdge[RiverSegment](rs)
  }

  val bridges: StorableByEdge[Bridge] = new StoreByEdge[Bridge](Map())

  val boats: StorableByEdge[Boat] = new StoreByEdge[Boat](Map())

  def floodHex(hexPos: HexPosition): Unit = {
    hexes.byPosition.get(hexPos).foreach(hex => hexes.update(hexPos, hex.flood))
  }

  def lavaFieldOnHex(hexPos: HexPosition): Unit = {
    hexes.byPosition.get(hexPos).foreach(hex => hexes.update(hexPos, hex.lavaFlow))
  }



}
