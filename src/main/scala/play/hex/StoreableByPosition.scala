package play.hex

trait StoreableByPosition[P, T] {

  private[hex] var byPosition: Map[P, T]
  private[hex] var toPosition: Map[T, P] = byPosition.map(_.swap)

  def toPos(t: T): Option[P] = toPosition.get(t)

  def at(p: P): Option[T] = byPosition.get(p)

  def size: Int = byPosition.size

  def all: Iterable[T] = byPosition.values

  // override to for custom logic in update
  protected def updateCustom(p: P, newT: T, oldT: T): Unit = {}
  protected def removeCustom(p: P, t: T): Unit = {}

  def update(p: P, newT: T): Unit = {
    byPosition.get(p).foreach(oldT => {
      toPosition -= oldT
      updateCustom(p, newT, oldT)
    })
    toPosition = toPosition.updated(newT, p)
    byPosition = byPosition.updated(p, newT)
  }

  def remove(p: P): Option[T] = {
    val maybeT = byPosition.get(p)
    maybeT.foreach({ t =>
      byPosition = byPosition - p
      toPosition = toPosition - t
      removeCustom(p, t)
    })
    maybeT
  }
}
