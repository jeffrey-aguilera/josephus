object Main {

  import us.aguilera.josephus._

  import scala.util.Try

  def main(args: Array[String]): Unit = {
    args match {
      case Array(IntExtractor(size), IntExtractor(step)) if size > 0 =>
        println(FastSurvivor(size, step))
      case _ =>
        // note: `step` may be negative => rotating around circle in opposite direction
        println("usage: sbt 'run size step' where size is positive")
    }
  }

  /** [[IntExtractor]] for poor man's parsing of command line arguments. */
  private object IntExtractor {
    def unapply(string: String): Option[Int] = {
      Try(string.toInt).toOption
    }
  }
}
