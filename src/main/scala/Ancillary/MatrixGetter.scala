package Ancillary

import Abstract2D.Ancillary.RectangularObject
import Abstract3D.Matrix

object MatrixGetter {

  def fromArray(array: Array[Array[Boolean]]): List[(Point, Matrix)] = {

    val buff = collection.mutable.ListBuffer.empty[(Point, Matrix)]
    val x_length = array.length
    val y_length = array(0).length

    val points = RectangularObject.buildBase(Point(), Point(x_length - 1, y_length - 1))
    for (point <- points) {
      val matrix = Array.fill(3, 3)(false)
      matrix(1)(1) = true
      for (p <- Perimeter.getPerimeter(List(point))) {
        if (p.x >= 0 && p.x <= x_length - 1 && p.y >= 0 && p.y <= y_length - 1)
          matrix(1 + (p - point).y)(1 + (p - point).x) = array(p.y)(p.x)
      }
      buff += Tuple2(point, Matrix(matrix))
    }

    buff.toList
  }

  def fromList(points: List[Point]): List[(Point, Matrix)] = {
    val buff = collection.mutable.ListBuffer.empty[(Point, Matrix)]

    for (point <- points) {
      val matrix = Array.fill(3, 3)(false)
      matrix(1)(1) = true

      for (p <- Perimeter.getPerimeter(List(point)))
        if (points.contains(p))
          matrix(1 - (point - p).y)(1 - (point - p).x) = true
      buff += Tuple2(point, Matrix(matrix))
    }
    buff.toList
  }
}
