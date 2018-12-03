import scala.io.Source

val txt = Source.fromFile("input.txt").getLines
val pattern = "#(\\d+) @ (\\d+),(\\d+): (\\d+)+x(\\d+)".r

case class Patch(offX: Int, offY: Int, width: Int, height: Int, id: Int)

val input = txt
  .map {
    case pattern(id, offX, offY, w, h) => Patch(offX.toInt, offY.toInt, w.toInt, h.toInt, id.toInt)
  }.toList


val exclude = input
  .flatMap(e => (0 until e.width).flatMap(x => (0 until e.height).map(y => (x + e.offX, y + e.offY, e.id))).toList)
  .groupBy(k => (k._1, k._2)).mapValues(_.map(_._3))
  .filter(_._2.length > 1)
  .flatMap { case (k, v) => v }
  .toSet

println(input.map(_.id).toSet.diff(exclude).toSeq.head)
