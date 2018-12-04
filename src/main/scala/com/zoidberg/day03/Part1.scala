package com.zoidberg.day03

import scala.io.Source

object Part1 {
  val pattern = "#\\d+ @ (\\d+),(\\d+): (\\d+)+x(\\d+)".r

  case class Patch(offX: Int, offY: Int, width: Int, height: Int)

  def main(args: Array[String]): Unit = {
    val txt = Source.fromResource("day03/input.txt").getLines
    val res = txt
      .map {
        case pattern(offX, offY, w, h) => Patch(offX.toInt, offY.toInt, w.toInt, h.toInt)
      }
      .flatMap(e => (0 until e.width).flatMap(x => (0 until e.height).map(y => (x + e.offX, y + e.offY))).toList).toList
      .groupBy(identity).mapValues(_.size)
      .filter {
        case (k, v) => v >= 2
      }
      .size

    println(res)
  }
}
