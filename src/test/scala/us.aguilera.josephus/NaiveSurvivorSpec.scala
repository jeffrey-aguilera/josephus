package us.aguilera.josephus

import org.specs2._

class NaiveSurvivorSpec extends mutable.Specification {

  "NaiveSurvivorSpec ..." >> {
    "cannot be determined for illegal arguments" in {
      NaiveSurvivor(0, 0) must throwAn[IllegalArgumentException]
    }

    "must evaluate to `1` when step is `0`" in {
      for (size <- 1 to 10)
        yield {
          NaiveSurvivor(size, 0) mustEqual 1
        }
    }

    "must evaluate to `size` when step is `1`" in {
      for (size <- 1 to 10)
        yield {
          NaiveSurvivor(size, 1) mustEqual size
        }
    }

    "must match hand-computed values" in {
      NaiveSurvivor(1, 2) mustEqual 1
      NaiveSurvivor(2, 2) mustEqual 1
      NaiveSurvivor(3, 2) mustEqual 3
      NaiveSurvivor(4, 2) mustEqual 1
    }

    "must be symmetric for negative step" in {
      for (size <- 1 to 10; step <- 0 to size)
        yield {
          // can go around circle either way, but need to renumber results
          NaiveSurvivor(size, -step) + NaiveSurvivor(size, step + 1) mustEqual size + 1
        }
    }
  }
}
