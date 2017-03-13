package us.aguilera.josephus

import org.specs2._

class FasterSurvivorSpec extends mutable.Specification {

  "FasterSurvivor ..." >> {
    "cannot be determined for illegal arguments" in {
      FasterSurvivor(0, 0) must throwAn[IllegalArgumentException]
    }

    "must evaluate to `1` when step is `0`" in {
      for (size <- 1 to 10)
        yield {
          FasterSurvivor(size, 0) mustEqual 1
        }
    }

    "must evaluate to `size` when step is `1`" in {
      for (size <- 1 to 10)
        yield {
          FasterSurvivor(size, 1) mustEqual size
        }
    }

    "must match hand-computed values" in {
      FasterSurvivor(1, 2) mustEqual 1
      FasterSurvivor(2, 2) mustEqual 1
      FasterSurvivor(3, 2) mustEqual 3
      FasterSurvivor(4, 2) mustEqual 1
    }

    "must be symmetric for negative step" in {
      for (size <- 1 to 10; step <- 0 to size)
        yield {
          // can go around circle either way, but need to renumber results
          FasterSurvivor(size, -step) + FasterSurvivor(size, step + 1) mustEqual size + 1
        }
    }

    "must match reference implementation" in {
      for (size <- 1 to 10; step <- 0 to 2 * size)
        yield {
          FasterSurvivor(size, step) mustEqual NaiveSurvivor(size, step)
        }
    }
  }
}
