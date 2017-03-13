package us.aguilera.josephus

/** [[NaiveCircle]] companion for generating the initial [[NaiveCircle]] of a given size. */
object NaiveCircle {

  /** @return [[NaiveCircle[Int]] of the given positive `size`.*/
  def apply(size: Int): NaiveCircle[Int] = {
    NaiveCircle((1 to size).toVector)
  }
}

/** [[NaiveCircle]] represents a circular arrangement of labelled elements with an imaginary cursor located before the
  * first element.
  *
  * @param elements opaque, ordered labels.
  * @tparam T type of labels, e.g., ordinal indexes or names.
  */
case class NaiveCircle[+T](private val elements: Vector[T]) extends (Int => T) {

  require(elements.nonEmpty, "Circle must include at least one element")

  def apply(index: Int): T = {
    // negative indexes go other direction
    elements(index mod size)
  }

  def size: Int = {
    elements.size
  }

  // FastSurvivor does not know how to handle negative `step`
  def reverse: NaiveCircle[T] = {
    NaiveCircle(elements.reverse)
  }

  def rotate(rotation: Int): NaiveCircle[T] = {
    NaiveCircle(elements.rotate(rotation))
  }

  def take(count: Int): Option[NaiveCircle[T]] = {
    if (count >= size) {
      Some(this)
    } else if (count > 0) {
      Some(NaiveCircle(elements.take(count)))
    } else {
      None
    }
  }

  /** Delete the element before `step` and reposition the cursor to the beginning of the new [[NaiveCircle]].
    *
    * This is equivalent to
    * {{{
    * rotate(step mod size).take(size - 1)
    * }}}
    * but the `rotate` and `take` are performed in one step.
    *
    * @param step between deleted elements.
    * @return A smaller [[Option[Circle[T]].
    */
  def delete(step: Int): Option[NaiveCircle[T]] = {
    if (size == 1) {
      None
    } else {
      val skip = step mod size
      if (skip == 0) {
        Some(NaiveCircle(elements.take(size - 1)))
      } else {
        Some(NaiveCircle(elements.drop(skip) ++ elements.take(skip - 1)))
      }
    }
  }

  /** Makes one pass through the circle deleting all multiples of `step`, provided it is not larger than `size`.
    * Otherwise, the single step `delete` method is used.
    *
    * @param step between deleted elements.
    * @return A smaller [[Option[Circle[T]].
    */
  def deleteMultiple(step: Int): Option[NaiveCircle[T]] = {

    // use reflection formula instead
    require(step >= 0)

    step match {
      case 0 =>
        Some(NaiveCircle(Vector(apply(0))))
      case 1 =>
        Some(NaiveCircle(Vector(apply(-1))))
      case k if k <= size =>
        // remove `size/step` entries at once
        NaiveCircle(elements.grouped(step).toVector.flatMap(_.take(step - 1))).rotate(-size % step).take(size - size / step)
      case _ =>
        delete(step)
    }
  }

  override def toString: String = {
    elements.mkString("(", ", ", ")")
  }
}
