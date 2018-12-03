import scala.io.Source

val txt = Source.fromFile("input.txt").getLines
val pattern = "#\\d+ @ (\\d+),(\\d+): (\\d+)+x(\\d+)".r

val res = txt
    .map(s => s match {
      case pattern(offX, offY, w, h) => (offX.toInt, offY.toInt, w.toInt, h.toInt)
    })
    .flatMap( e => (0 until e._3).flatMap(x => (0 until e._4).map( y => (x + e._1, y + e._2) )).toList).toList
    .groupBy(identity).mapValues(_.size)
    .filter((t) => t._2 >= 2)
    .size

println(res)