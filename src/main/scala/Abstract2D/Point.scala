package Abstract2D

class Point private(val x: Int, val y: Int) {
  def +(other: Point) = Point(x + other.x, y + other.y)

  def -(other: Point) = Point(x - other.x, y - other.y)

  def unary_- = Point(-x, -y)

  def distance(other: Point): Double =
    Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2))

  def stepDistance(other: Point): Int =
    Math.abs(x - other.x) + Math.abs(y - other.y)

  override def toString: String = "(" + x.toString + "," + y.toString + ")"

  override def hashCode: Int = 41 * (41 + x) + y

  override def equals(other: Any): Boolean = other match {
    case that: Point =>
      (that canEqual this) && (this.x == that.x) && (this.y == that.y)
    case _ =>
      false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Point]
}

object Point {
  def apply(x: Int, y: Int) = new Point(x, y)

  def apply() = new Point(0, 0)

  def apply(prototype: Point) = new Point(prototype.x, prototype.y)
}
