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
        (new FastSurvivor(-step + 1))(Circle(size).reverse)
      case _ =>
        (new FastSurvivor(step))(Circle(size))
    }
  }

  def debug(sizes: Range, steps: Range): Unit = {
    val start = System.nanoTime()
    for (size <- sizes; step <- steps) {
      FastSurvivor(size, step)
    }
    val nanos = System.nanoTime() - start
    println(s"FastSurvivor($sizes, $steps) took ${nanos/1000000} ms")
  }
}

/** [[FastSurvivor]] iterates `deleteMultiple` on a [[Circle]] to determine the surviving label. */
private class FastSurvivor[T](step: Int) extends (Circle[T] => T) {

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
