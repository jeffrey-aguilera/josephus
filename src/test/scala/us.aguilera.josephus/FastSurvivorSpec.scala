package us.aguilera.josephus

import org.specs2._

class FastSurvivorSpec extends mutable.Specification {

  "FastSurvivor ..." >> {
    "cannot be determined for illegal arguments" in {
      FastSurvivor(0, 0) must throwAn[IllegalArgumentException]
    }

    "must evaluate to `1` when step is `0`" in {
      for (size <- 1 to 10)
        yield {
          FastSurvivor(size, 0) mustEqual 1
        }
    }

    "must evaluate to `size` when step is `1`" in {
      for (size <- 1 to 10)
        yield {
          FastSurvivor(size, 1) mustEqual size
        }
    }

    "must match hand-computed values" in {
      FastSurvivor(1, 2) mustEqual 1
      FastSurvivor(2, 2) mustEqual 1
      FastSurvivor(3, 2) mustEqual 3
      FastSurvivor(4, 2) mustEqual 1
    }

    "must be symmetric for negative step" in {
      for (size <- 1 to 10; step <- 0 to size)
        yield {
          // can go around circle either way, but need to renumber results
          FastSurvivor(size, -step) + FastSurvivor(size, step + 1) mustEqual size + 1
        }
    }

    "must match reference implementation" in {
      for (size <- 1 to 10; step <- 0 to 2 * size)
        yield {
          FastSurvivor(size, step) mustEqual NaiveSurvivor(size, step)
        }
    }
  }
}
