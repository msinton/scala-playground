package play.hex

import play.hex.stores._
import play.river.{RiverNetwork, RiverSegment}

import scala.util.Random

// TODO replace wall with this
sealed trait Wall
case object WoodWall extends Wall
case object StoneWall extends Wall

case class Person()

class BoardData(numPlayers: Int) {
  
  private def setupHexes: Iterable[(HexPosition, Hex)] = ???
//  val typeWeightings = TypeWeightings() - in setup

  
  val hexes: HexStore = new HexStore(setupHexes.toMap)

  val people: StoreableByHexPosition[Person] = new StoreByHexPosition(Map.empty[HexPosition, Person])
  val walls: StoreableByHexPosition[Wall] = new StoreByHexPosition(Map.empty[HexPosition, Wall])

  val random = new Random()

  // TODO think about groups - what I will do with them beyond testing
  val rivers: StoreableByEdge[RiverSegment] = {
    val (rs, _) = new RiverNetwork(random, hexes).generate(numPlayers)
    new StoreByEdge[RiverSegment](rs)
  }

  // rivers
  // bridges
  // boats

  def floodHex(hexPos: HexPosition): Unit = {
    hexes.byPosition.get(hexPos).foreach(hex => hexes.update(hexPos, hex.flood))
  }

  def lavaFieldOnHex(hexPos: HexPosition): Unit = {
    hexes.byPosition.get(hexPos).foreach(hex => hexes.update(hexPos, hex.lavaFlow))
  }



}
