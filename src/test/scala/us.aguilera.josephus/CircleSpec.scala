package us.aguilera.josephus

import org.specs2._

class CircleSpec extends mutable.Specification {

  "Circle ..." >> {
    "cannot be instantiated without elements" in {
      Circle(0) must throwAn[IllegalArgumentException]
      Circle(-1) must throwAn[IllegalArgumentException]
    }

    "does not allow deletion of the last element" in {
      Circle(1).delete(0) mustEqual None
      Circle(1).delete(1) mustEqual None
    }

    "must produce a smaller circle on deletion" in {
      for (size <- 2 to 10; step <- -size to size)
        yield {
          Circle(size).delete(step).map(_.size) mustEqual Some(size - 1)
        }
    }

    "must produce non-permutated, next smaller circle on deletion of last element" in {
      Circle(1).delete(0) must be(None)
      for (size <- 2 to 10)
        yield {
          Circle(size).delete(0) mustEqual Some(Circle(size - 1))
        }
    }

    "must act as a circular structure" in {
      for (size <- 1 to 10; step <- -size to size; multiple <- -3 to 3)
        yield {
          Circle(size).delete(step) mustEqual Circle(size).delete(step + multiple * size)
        }
    }
  }
}
