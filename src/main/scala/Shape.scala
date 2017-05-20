import scala.util.Random

object Shape extends Enumeration {
  type Shape = Value
  val T, +, |, L, - = Value

  def randomShape(): Shape = Shape(Random.nextInt(Shape.maxId))
}