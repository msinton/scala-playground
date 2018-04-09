package play.utils
import scala.collection.mutable

object Utils {

  type ToProperties[A, B] = A => Traversable[B]

  /**
    * For items in a list which have some property that can be viewed as a list, this creates an inverse mapping from
    * those properties to the items.
    *
    * e.g.
    * You have a list of strings and you want a mapping of characters to the strings that contain said character.
    * createInverseMapping(list)(s.toCharArray)
    *
    * @param list The list of items to create the mapping from
    * @param f from item to properties that will be the keys in the map
    * @param g from item to desired value that represents that item
    * @tparam Y the type of the item
    * @tparam X the type of the properties
    * @tparam Z the type of the values
    * @return An inverse mapping from the properties to the items as a Set
    */
  def createInverseMappingWithValueTransform[Y, X <: AnyRef, Z](list: Seq[Y])
                                                               (g: Y => Z)
                                                               (f: ToProperties[Y, X]): (collection.Map[X, Set[Z]]) = {

    val map = mutable.AnyRefMap.empty[X, Set[Z]]

    for (item <- list) {
      val xs = f(item)
      for (x <- xs) {
        map.update(x, map.getOrElse(x, Set()) + g(item))
      }
    }
    map
  }

  /**
    * For items in a list which have some property that can be viewed as a list, this creates an inverse mapping from
    * those properties to the items.
    *
    * e.g.
    * You have a list of strings and you want a mapping of characters to the strings that contain said character.
    * createInverseMapping(list)(s.toCharArray)
    *
    *
    * @param list The list of items to create the mapping from
    * @param f from item to properties that will be the keys in the map
    * @tparam X the type of the properties
    * @tparam Z the type of the item
    * @return An inverse mapping from the properties to the items as a Set
    */
  def createInverseMapping[X <: AnyRef, Z](list: Seq[Z])(f: ToProperties[Z, X]): (collection.Map[X, Set[Z]]) = {
    createInverseMappingWithValueTransform[Z, X, Z](list)({ z: Z => z})(f)
  }

}