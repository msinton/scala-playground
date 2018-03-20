package play

import play.hex._

// TODO rename as Edge
case class BordersHex(hexPosition: HexPosition, side: Side)

object BordersHex {

  /**
    * Normalize BordersHex so that Side is only ever S, SE or SW.
    * Then equality works as expected without needing to be overridden.
    */
  def apply(p: HexPosition, side: Side): BordersHex = {
    side match {
      case N => new BordersHex(p.next(N), S)
      case NE => new BordersHex(p.next(NE), SW)
      case NW => new BordersHex(p.next(NW), SE)
      case _ => new BordersHex(p, side)
    }
  }
}