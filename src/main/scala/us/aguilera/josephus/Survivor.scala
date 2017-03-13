package us.aguilera.josephus

object Survivor extends ((Int, Int) => Int) {

  def apply(size: Int, step: Int): Int = {
    size match {
      case n if n > 0 =>
        if (step < 0) {
          // go around circle in opposite direction
          (size + 1) - Survivor(size, 1 - step)
        } else if (step == 0) {
          1
        } else if (step == 1) {
          size
        } else if (step < size) {
          // recurse on exponentially decreasing sizes
          val smaller = Survivor(size - size/step, step)
          if (smaller <= size % step) {
            smaller + (size / step) * step
          } else {
            (step * ((smaller - (size % step)) - 1)) / (step - 1) + 1
          }
        } else {
          // convert primitive recursion to iteration
          (new Survivor(step))(size)
        }
      case _ =>
        throw new IllegalArgumentException(s"size must be positive, not $size")
    }
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