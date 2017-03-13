package us.aguilera

package object josephus {

  /** [[RichInt]] enhances [[Int]] by adding a `mod` function that produces a positive result. */
  implicit final class RichInt(val i: Int) extends AnyVal {

    // `i % n` has same sign as `i` -- this method ensures result is between `0` and `n-1`, assuming `n > 0`
    def mod(n: Int): Int = {
      val result = i % n
      if (result < 0) {
        result + n
      } else {
        result
      }
    }
  }

  /** [[RichIndexedSeq]] enhances an [[IndexedSeq]] with a `rotate` method. */
  implicit final class RichIndexedSeq[+T](val seq: IndexedSeq[T]) extends AnyVal {

    def rotate(rotation: Int): IndexedSeq[T] = {
      val split = rotation mod seq.length
      if (split == 0) {
        seq
      } else {
        val (before, after) = seq.splitAt(split)
        after ++ before
      }
    }
  }

  /** [[RichVector]] enhances a [[Vector]] with a `rotate` method. */
  implicit final class RichVector[+T](val vector: Vector[T]) extends AnyVal {

    def rotate(rotation: Int): Vector[T] = {
      val split = rotation mod vector.length
      if (split == 0) {
        vector
      } else {
        val (before, after) = vector.splitAt(split)
        after ++ before
      }
    }
  }
}
