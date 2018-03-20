import scala.collection.mutable

(0 to 3).scanLeft(5)(_ + _).tail

def mem = {
  val total = Runtime.getRuntime.totalMemory()
  val free = Runtime.getRuntime.freeMemory()
  total - free
}

// 142116864
// 60703392
// 112365624
// 162782152
val mem1 = mem

case class A(id: Int, aType: String) extends Comparable[A] {
  override def compareTo(o: A): Int = {
    val typeComparision = aType.compareTo(o.aType)
    if (typeComparision != 0) typeComparision
    else id.compareTo(o.id)
  }
}

val m1 = mutable.TreeMap.empty[A, Int]

(0 to 100).foreach(x => m1.update(A(x, x % 4 + ""), x))

mem1 - mem

// 133054480
// 57853552

