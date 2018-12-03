import scala.io.Source

val txt = Source.fromFile("input.txt").getLines
val pattern = "#(\\d+) @ (\\d+),(\\d+): (\\d+)+x(\\d+)".r

val input = txt
    .map(s => s match {
      case pattern(id, offX, offY, w, h) => (offX.toInt, offY.toInt, w.toInt, h.toInt, id)
    }).toList


val exclude = input
    .flatMap( e => (0 until e._3).flatMap(x => (0 until e._4).map( y => (x + e._1, y + e._2, e._5) )).toList)
    .groupBy(k => (k._1, k._2)).mapValues(_.map(_._3))
    .filter(_._2.length > 1)
    .flatMap(kv => kv._2)
    .toSet

println(input.map(_._5).toSet.diff(exclude).toSeq.head)
