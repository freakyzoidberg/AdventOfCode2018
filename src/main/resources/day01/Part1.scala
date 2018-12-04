package com.zoidberg.day01

import scala.io.Source

object Part1 {
  val res = Source.fromFile("input.txt").getLines.map(_.toInt).reduce(_ + _)
  println(res)
}
