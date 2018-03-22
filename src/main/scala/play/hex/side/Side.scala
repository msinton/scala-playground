package play.hex.side

sealed trait Side {
  def clockwise: Side
  def antiClockwise: Side
  def opposite: Side
}

case object N extends Side {
  override val clockwise: Side = NE
  override val antiClockwise: Side = NW
  override val opposite: Side = S
}
case object NE extends Side {
  override val clockwise: Side = SE
  override val antiClockwise: Side = N
  override val opposite: Side = SW
}
case object SE extends Side {
  override val clockwise: Side = S
  override val antiClockwise: Side = NE
  override val opposite: Side = NW
}
case object S extends Side {
  override val clockwise: Side = SW
  override val antiClockwise: Side = SE
  override val opposite: Side = N
}
case object SW extends Side {
  override val clockwise: Side = NW
  override val antiClockwise: Side = S
  override val opposite: Side = NE
}
case object NW extends Side {
  override val clockwise: Side = N
  override val antiClockwise: Side = SW
  override val opposite: Side = SE
}
