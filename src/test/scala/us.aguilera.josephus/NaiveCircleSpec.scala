package us.aguilera.josephus

import org.specs2._

class NaiveCircleSpec extends mutable.Specification {

  "NaiveCircle ..." >> {
    "cannot be instantiated without elements" in {
      NaiveCircle(0) must throwAn[IllegalArgumentException]
      NaiveCircle(-1) must throwAn[IllegalArgumentException]
    }

    "does not allow deletion of the last element" in {
      NaiveCircle(1).delete(0) mustEqual None
      NaiveCircle(1).delete(1) mustEqual None
    }

    "must produce a smaller circle on deletion" in {
      for (size <- 2 to 10; step <- -size to size)
        yield {
          NaiveCircle(size).delete(step).map(_.size) mustEqual Some(size - 1)
        }
    }

    "must produce non-permutated, next smaller circle on deletion of last element" in {
      NaiveCircle(1).delete(0) must be(None)
      for (size <- 2 to 10)
        yield {
          NaiveCircle(size).delete(0) mustEqual Some(NaiveCircle(size - 1))
        }
    }

    "must act as a circular structure" in {
      for (size <- 1 to 10; step <- -size to size; multiple <- -3 to 3)
        yield {
          NaiveCircle(size).delete(step) mustEqual NaiveCircle(size).delete(step + multiple * size)
        }
    }
  }
}
