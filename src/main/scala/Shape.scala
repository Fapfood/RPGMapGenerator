package main.scala

import scala.util.Random

object Shape extends Enumeration {
  type Shape = Value
  val T, +, |, L, - = Value

  def randomShape(): Shape = {
    val x: Int = Random.nextInt(Shape.maxId)
    Shape(x)
  }
}