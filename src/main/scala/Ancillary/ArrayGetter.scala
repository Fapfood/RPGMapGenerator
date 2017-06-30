package Ancillary

import Abstract2D.Ancillary.RectangularObject

object ArrayGetter {

  def fromArray(array: Array[Array[Boolean]]): List[(Point, Array[Array[Boolean]])] = {

    val buff = collection.mutable.ListBuffer.empty[(Point, Array[Array[Boolean]])]
    val x_length = array.length
    val y_length = array(0).length

    val points = RectangularObject.buildBase(Point(), Point(x_length - 1, y_length - 1))
    for (point <- points) {
      val matrix = Array.fill(3, 3)(false)
      matrix(1)(1) = true
      for (p <- Perimeter.getPerimeter(List(point))) {
        if (p.x >= 0 && p.x <= x_length - 1 && p.y >= 0 && p.y <= y_length - 1)
          matrix(1 + (p - point).x)(1 + (p - point).y) = array(p.x)(p.y)
      }
      buff += Tuple2(point, matrix)
    }

    buff.toList
  }

  def fromList(points: List[Point]): List[(Point, Array[Array[Boolean]])] = {
    val buff = collection.mutable.ListBuffer.empty[(Point, Array[Array[Boolean]])]

    for (point <- points) {
      val matrix = Array.fill(3, 3)(false)
      matrix(1)(1) = true

      for (p <- Perimeter.getPerimeter(List(point)))
        if (points.contains(p))
          matrix(1 - (point - p).x)(1 - (point - p).y) = true
      buff += Tuple2(point, matrix)
    }
    buff.toList
  }
}
