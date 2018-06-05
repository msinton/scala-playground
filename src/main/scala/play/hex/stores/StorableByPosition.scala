package play.hex.stores

trait StorableByPosition[P, T] {

  private[hex] var byPosition: Map[P, T]
  private[hex] var toPosition: Map[T, P] = byPosition.map(_.swap)

  def toPos(t: T): Option[P] = toPosition.get(t)

  def atPos(p: P): Option[T] = byPosition.get(p)

  def size: Int = byPosition.size

  def all: Iterable[T] = byPosition.values

  // override for custom logic in update and remove
  protected def updateCustom(p: P, newT: T, oldT: T): Unit = {}
  protected def updateCustom(p: P, newT: T): Unit = {}
  protected def removeCustom(p: P, t: T): Unit = {}

  def update(p: P, newT: T): Unit = {
    byPosition.get(p).foreach(oldT => {
      toPosition -= oldT
      updateCustom(p, newT, oldT)
    })
    updateCustom(p, newT)
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
