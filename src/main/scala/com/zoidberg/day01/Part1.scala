package com.zoidberg.day01

import scala.io.Source

object Part1 {
  def main(args: Array[String]): Unit = {
    val res = Source.fromResource("day01/input.txt").getLines.map(_.toInt).reduce(_ + _)
    println(res)
  }
}
