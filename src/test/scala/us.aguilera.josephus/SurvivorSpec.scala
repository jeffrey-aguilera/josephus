package us.aguilera.josephus

import org.specs2._

class SurvivorSpec extends mutable.Specification {

  "Survivor ..." >> {
    "cannot be determined for illegal arguments" in {
      Survivor(0, 0) must throwAn[IllegalArgumentException]
    }

    "must evaluate to `1` when step is `0`" in {
      for (size <- 1 to 10)
        yield {
          Survivor(size, 0) mustEqual 1
        }
    }

    "must evaluate to `size` when step is `1`" in {
      for (size <- 1 to 10)
        yield {
          Survivor(size, 1) mustEqual size
        }
    }

    "must match hand-computed values" in {
      Survivor(1, 2) mustEqual 1
      Survivor(2, 2) mustEqual 1
      Survivor(3, 2) mustEqual 3
      Survivor(4, 2) mustEqual 1
    }

    "must be symmetric for negative step" in {
      for (size <- 1 to 10; step <- 0 to size)
        yield {
          // can go around circle either way, but need to renumber results
          Survivor(size, -step) + Survivor(size, step + 1) mustEqual size + 1
        }
    }

    "must match reference implementation" in {
      for (size <- 1 to 10; step <- 0 to 2 * size)
        yield {
          Survivor(size, step) mustEqual NaiveSurvivor(size, step)
        }
    }
  }
}
