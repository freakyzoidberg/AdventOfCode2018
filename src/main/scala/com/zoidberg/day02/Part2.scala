package com.zoidberg.day02

import scala.io.Source

object Part2 {
  def diffString(t: List[String]) = t(0).zipWithIndex.filter(a => a._1 == t(1)(a._2))

  def main(args: Array[String]): Unit = {
    val txt = Source.fromResource("day02/input.txt").getLines

    val res = txt
      .toList
      .combinations(2)
      .filter(t => diffString(t).length == t(0).length - 1)
      .flatMap(t => diffString(t).map(_._1))
      .mkString("")

    println(res)
  }
}
