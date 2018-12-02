import scala.io.Source


val list = Source.fromFile("input.txt").getLines.map(_.toInt).toStream
Stream
  .continually(list)
  .flatten
  .foldLeft((0, Set[Int](), Option.empty[Int])) { (acc, n) =>
    val freq = acc._1 + n
    if (acc._2.contains(freq)) {
      println(freq)
      System.exit(1) // yeah ...
      (freq, acc._2, Some(freq))
    } else {
      (freq, acc._2 + freq, Option.empty[Int])
    }
  }
