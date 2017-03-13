package us.aguilera.josephus

object Benchmark {

  // very poor man's benchmark --- should use JMH in production
  def apply(solver: (Int, Int) => Int)(sizes: Range, steps: Range): Unit = {
    val start = System.nanoTime()
    for (size <- sizes; step <- steps) {
      solver(size, step)
    }
    val nanos = System.nanoTime() - start
    println(s"${nanos/1000000} ms")
  }
}
