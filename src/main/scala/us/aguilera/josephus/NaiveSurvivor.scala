package us.aguilera.josephus

object NaiveSurvivor extends ((Int, Int) => Int) {

  def apply(size: Int, step: Int): Int = {
    // do not use trivial forms here so that they are usable as test cases
    (new NaiveSurvivor(step))(Circle(size))
  }
}

/** [[NaiveSurvivor]] iterates `delete` on a [[Circle]] to determine the surviving label. */
private class NaiveSurvivor[T](step: Int) extends (Circle[T] => T) {

  @annotation.tailrec
  final def apply(circle: Circle[T]): T = {
    circle.delete(step) match {
      case None =>
        circle(0)
      case Some(smallerCircle) =>
        apply(smallerCircle)
    }
  }
}
