package com.zoidberg.day04

import org.joda.time.{DateTime}

import scala.io.Source

object Part2 {

  sealed trait Log {
    val date: DateTime
  }

  case class Sleep(override val date: DateTime) extends Log

  case class Wakes(override val date: DateTime) extends Log

  case class Shift(override val date: DateTime, id: Int) extends Log

  /**
    * [1518-04-22 00:56] falls asleep
    * [1518-07-23 00:09] falls asleep
    * [1518-10-29 00:02] falls asleep
    * [1518-07-29 00:58] wakes up
    * [1518-03-18 00:15] falls asleep
    * [1518-11-08 00:02] Guard #2851 begins shift
    */

  val fallsAsleep = "\\[(.*)\\] falls asleep".r
  val wakesUp = "\\[(.*)\\] wakes up".r
  val beginShift = "\\[(.*)\\] Guard #(\\d+) begins shift".r

  import org.joda.time.format.DateTimeFormat
  import org.joda.time.format.DateTimeFormatter

  val fmt: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")


  def main(args: Array[String]): Unit = {
    case class State(current: Int, sleep: Option[DateTime], shifts: Map[Int, Seq[Int]])

    implicit def dateTimeOrdering: Ordering[DateTime] = Ordering.fromLessThan(_ isBefore _)

    val tmp = Source.fromResource("day04/input.txt")
      .getLines
      .map {
        case fallsAsleep(date) => Sleep(fmt.parseDateTime(date))
        case wakesUp(date) => Wakes(fmt.parseDateTime(date))
        case beginShift(date, id) => Shift(fmt.parseDateTime(date), id.toInt)
      }.toList
      .sortBy(_.date)
    val txt = tmp
      .foldLeft {State(0, None, Map.empty[Int, Seq[Int]])} { (acc, e) =>
        e match {
          case Shift(d, id) =>  State(id, None,
            if (acc.shifts.contains(id))
              acc.shifts
            else
              acc.shifts + (id -> Seq.empty[Int])
          )
          case Sleep(d) => State(acc.current, Some(d), acc.shifts)
          case Wakes(d) => {
            val res = acc.shifts(acc.current) ++ (acc.sleep.get.minuteOfHour().get() until d.minuteOfHour().get).toList
            State(acc.current, None, acc.shifts + (acc.current -> res))
          }
        }
      }
    val res = txt
      .shifts
      .toList
      .map { v =>  (v._1,  v._2.groupBy(identity).mapValues(_.length)) }
      .filter(_._2.size > 0)
      .map { v =>
        val (minute:Int, duration:Int) = v._2.maxBy(_._2)
        (v._1, minute, duration)
      }

    println(res.maxBy(_._3))
  }
}

