package us.aguilera.josephus

/** [[Circle]] companion for generating the initial [[Circle]] of a given size. */
object Circle {

  /** @return [[Circle[Int]] of the given positive `size`.*/
  def apply(size: Int): Circle[Int] = {
    Circle((1 to size).toVector)
  }
}

/** [[Circle]] represents a circular arrangement of labelled elements with an imaginary cursor located before the
  * first element.
  *
  * @param elements opaque, ordered labels.
  * @tparam T type of labels, e.g., ordinal indexes or names.
  */
case class Circle[+T](private val elements: Vector[T]) extends (Int => T) {

  require(elements.nonEmpty, "Circle must include at least one element")

  def apply(index: Int): T = {
    // negative indexes go other direction
    elements(index mod size)
  }

  def size: Int = {
    elements.size
  }

  // FastSurvivor does not know how to handle negative `step`
  def reverse: Circle[T] = {
    Circle(elements.reverse)
  }

  def rotate(rotation: Int): Circle[T] = {
    Circle(elements.rotate(rotation))
  }

  def take(count: Int): Option[Circle[T]] = {
    if (count > 0) {
      Some(Circle(elements.take(count)))
    } else {
      None
    }
  }

  /** Delete the element before `step` and reposition the cursor to the beginning of the new [[Circle]].
    *
    * @param step between deleted elements.
    * @return A smaller [[Option[Circle[T]].
    */
  def delete(step: Int): Option[Circle[T]] = {
    rotate(step mod size).take(size - 1)
  }

  /** Makes one pass through the circle deleting all multiples of `step`, provided it is not larger than `size`.
    * Otherwise, the single step `delete` method is used.
    *
    * @param step between deleted elements.
    * @return A smaller [[Option[Circle[T]].
    */
  def deleteMultiple(step: Int): Option[Circle[T]] = {

    // use reflection formula instead
    require(step >= 0)

    step match {
      case 0 =>
        Some(Circle(Vector(apply(0))))
      case 1 =>
        Some(Circle(Vector(apply(-1))))
      case k if k <= size =>
        // remove `size/step` entries in one pass - be careful where the iterator is converted to a vector!
        // the tricky part referred to in `README.md` is `rotate(-size % step)`
        Circle(elements.grouped(step).flatMap(_.take(step - 1)).toVector).rotate(-size % step).take(size - size / step)
      case _ =>
        delete(step)
    }
  }

  override def toString: String = {
    elements.mkString("(", ", ", ")")
  }
}
