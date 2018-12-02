import scala.io.Source

val res = Source.fromFile("input.txt").getLines.map(_.toInt).reduce(_+_)

println(res)
