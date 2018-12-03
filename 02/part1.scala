import scala.io.Source

val txt = Source.fromFile("input.txt").getLines

val res = txt.map { s =>
  implicit def bool2int(b: Boolean) = if (b) 1 else 0

  val kv = s.groupBy(_.toLower).map(e => (e._1, e._2.length()))
  (
    (kv.filter(e => e._2 == 2).size > 0).toInt,
    (kv.filter(e => e._2 == 3).size > 0).toInt
  )
}.reduce((a, b) => (a._1 + b._1, a._2 + b._2))

println(res._1 * res._2)
