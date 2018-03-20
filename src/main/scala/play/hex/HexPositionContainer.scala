package play.hex

class HexPositionContainer[T] {

  var byPosition = Map.empty[HexPosition, T]
  var toPosition = Map.empty[T, HexPosition]

  def to(t: T): Option[HexPosition] = toPosition.get(t)
  def from(x: Int, y: Int): Option[T] = byPosition.get(HexPosition(x, y))
  def from(p: HexPosition): Option[T] = byPosition.get(p)

  // override to get extra functionality in update
  def updateExtra(p: HexPosition, newT: T, oldT: T): Unit = {
  }

  def update(p: HexPosition, newT: T): Unit = {
    byPosition.get(p).foreach(oldT => {
      toPosition -= oldT
      updateExtra(p, newT, oldT)
    })
    toPosition = toPosition.updated(newT, p)
    byPosition = byPosition.updated(p, newT)
  }
}
