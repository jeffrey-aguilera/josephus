package us.aguilera.josephus

object Survivor {

  def apply(size: Int, step: Int): Int = {
    size match {
      case n if n > 0 =>
        if (step < 0) {
          // go around circle in opposite direction
          (size + 1) - Survivor(size, 1 - step)
        } else {
          // convert primitive recursion to iteration
          (new Survivor(step))(size)
        }
      case _ =>
        throw new IllegalArgumentException(s"size must be positive, not $size")
    }
  }

  def debug(sizes: Range, steps: Range): Unit = {
    val start = System.nanoTime()
    for (size <- sizes; step <- steps) {
      Survivor(size, step)
    }
    val nanos = System.nanoTime() - start
    println(s"Survivor($sizes, $steps) took ${nanos/1000000} ms")
  }
}

/** [[Survivor]] converts the primitive recursion formula,
  * {{{
  * Survivor(n, k) = ((Survivor(n - 1, k) + k - 1) mod n) + 1
  * }}}
  * to an iterative fold to prevent premature stack overflow.
  */
private class Survivor(step: Int) extends (Int => Int) {

  def apply(size: Int): Int = {
    (1 to size).fold(1) {
      case (value, n) =>
        ((value + step - 1) mod n) + 1
    }
  }
}