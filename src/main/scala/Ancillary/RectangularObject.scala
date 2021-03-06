package Ancillary

object RectangularObject {
  def buildBase(leftTop: Point, rightBottom: Point): List[Point] = {
    val buff = collection.mutable.ListBuffer.empty[Point]

    for (x <- leftTop.x to rightBottom.x)
      for (y <- leftTop.y to rightBottom.y)
        buff += Point(x, y)

    buff.toList
  }
}