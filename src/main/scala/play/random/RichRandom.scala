package play.random

import play.utils.HasWeighting

import scala.annotation.tailrec
import scala.collection.mutable
import scala.util.Random


class RichRandom(random: Random) {

  /**
    * removes an element randomly from the buffer and returns it.
    *
    * @tparam T the type contained by the buffer
    * @return Some(element) removed. None if the list was empty
    */
  def randomRemove[T](list: mutable.Buffer[T]): Option[T] = {
    if (list.nonEmpty) {
      val e = list.remove(random.nextInt(list.size))
      Option(e)
    }
    else
      None
  }

  def getRandomWeighted[T](list: List[T], weightingsTotal: Int, weighting: T => Int): T = {
    @tailrec
    def getElement(n: Int, acc: Int, x: T, xs: List[T]): T = {
      if (n < acc || xs.isEmpty) {
        x
      } else {
        getElement(n, acc + weighting(x), xs.head, xs.tail)
      }
    }
    getElement(random.nextInt(weightingsTotal), 0, list.head, list.tail)
  }

  def getRandomWeighted[T <: HasWeighting](list: List[T], weightingsTotal: Int): T =
    getRandomWeighted(list, weightingsTotal, {t: T => t.weighting})

  def getRandom[T](list: Iterable[T]): T = {
    val index = random.nextInt(list.size)
    list.toBuffer(index)
  }
  def sample[T](list: IndexedSeq[T]): Option[T] = {
    if (list.isEmpty) None else {
      val index = random.nextInt(list.size)
      Option(list(index))
    }
  }

  def sample[T](list: IndexedSeq[T], n: Int): IndexedSeq[T] = {
    if (list.size <= n) list else {
      val indices = list.indices.toBuffer
      val result = mutable.ArrayBuffer.empty[T]
      while (result.size < n) {
        val ind = indices.remove(random.nextInt(indices.size))
        result.append(list(ind))
      }
      result
    }
  }
}
