package us.aguilera.josephus

object FasterSurvivor extends ((Int, Int) => Int) {

  def apply(size: Int, step: Int): Int = {
    if (size <= 0)
      throw new IllegalArgumentException(s"size must be positive, not $size")

    step match {
      case 0 =>
        1
      case 1 =>
        size
      case k if k < 0 =>
        (new FasterSurvivor(-step + 1))(Circle(size).reverse)
      case _ =>
        (new FasterSurvivor(step))(Circle(size))
    }
  }
}

/** [[FasterSurvivor]] iterates `deleteMultiple` on a [[Circle]] to determine the surviving label. */
private class FasterSurvivor[T](step: Int) extends (Circle[T] => T) {

  @annotation.tailrec
  final def apply(circle: Circle[T]): T = {
    circle.deleteMultiple(step) match {
      case None =>
        circle(0)
      case Some(smallerCircle) =>
        apply(smallerCircle)
    }
  }
}
