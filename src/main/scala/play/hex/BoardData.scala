package play.hex

import play.river.{RiverNetwork, RiverSegment}

import scala.util.Random

case class Wall()
case class Person()

class BoardData(numPlayers: Int) {
  
  private def setupHexes: Iterable[(HexPosition, Hex)] = ???
//  val typeWeightings = TypeWeightings() - in setup
  
  private def createHexesContainer(hexes: Iterable[(HexPosition, Hex)]): HexStore = {
    new HexStore(hexes.toMap)
  }
  
  val hexes: HexStore = createHexesContainer(setupHexes)

  val people: StoreableByHexPosition[Person] = new StoreByHexPosition(Map.empty[HexPosition, Person])
  val walls: StoreableByHexPosition[Wall] = new StoreByHexPosition(Map.empty[HexPosition, Wall])

  val random = new Random()

  // TODO discard rs
  // think about groups - what I will do with them beyond testing
  val (rs, gs) = new RiverNetwork(random, hexes).generate(numPlayers)
  val rivers: StoreableByEdge[RiverSegment] = new StoreByEdge[RiverSegment](rs)
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
