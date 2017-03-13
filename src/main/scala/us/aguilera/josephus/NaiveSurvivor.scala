package us.aguilera.josephus

object NaiveSurvivor {

  def apply(size: Int, step: Int): Int = {
    // do not use trivial forms here so that they are usable as test cases
    (new NaiveSurvivor(step))(NaiveCircle(size))
  }

  def debug(sizes: Range, steps: Range): Unit = {
    val start = System.nanoTime()
    for (size <- sizes; step <- steps) {
      NaiveSurvivor(size, step)
    }
    val nanos = System.nanoTime() - start
    println(s"NaiveSurvivor($sizes,$steps) took ${nanos/1000000} ms")
  }
}

/** [[NaiveSurvivor]] iterates `delete` on a [[NaiveCircle]] to determine the surviving label. */
private class NaiveSurvivor(step: Int) {

  @annotation.tailrec
  final def apply[T](circle: NaiveCircle[T]): T = {
    circle.delete(step) match {
      case None =>
        circle(0)
      case Some(smallerCircle) =>
        apply(smallerCircle)
    }
  }
}
