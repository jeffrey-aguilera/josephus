package us.aguilera.josephus

object FastSurvivor {

  def apply(size: Int, step: Int): Int = {
    if (size <= 0)
      throw new IllegalArgumentException(s"size must be positive, not $size")

    step match {
      case 0 =>
        1
      case 1 =>
        size
      case k if k < 0 =>
        (new FastSurvivor(-step + 1))(NaiveCircle(size).reverse)
      case _ =>
        (new FastSurvivor(step))(NaiveCircle(size))
    }
  }

  def debug(sizes: Range, steps: Range): Unit = {
    val start = System.nanoTime()
    for (size <- sizes; step <- steps) {
      FastSurvivor(size, step)
    }
    val nanos = System.nanoTime() - start
    println(s"FastSurvivor($sizes,$steps) took ${nanos/1000000} ms")
  }
}

/** [[FastSurvivor]] iterates `deleteMultiple` on a [[NaiveCircle]] to determine the surviving label. */
private class FastSurvivor(step: Int) {

  @annotation.tailrec
  final def apply[T](circle: NaiveCircle[T]): T = {
    circle.deleteMultiple(step) match {
      case None =>
        circle(0)
      case Some(smallerCircle) =>
        apply(smallerCircle)
    }
  }
}
