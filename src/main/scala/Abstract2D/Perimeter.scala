package Abstract2D

import Ancillary.Point

trait Perimeter {
  protected def getPerimeter(shape: List[Point]): List[Point] = {
    val buff = collection.mutable.ListBuffer.empty[Point]

    for (p <- shape)
      for (consideredPoint <- List(
        p - Point(1, 0), p + Point(1, 0), p - Point(0, 1), p + Point(0, 1),
        p + Point(1, 1), p - Point(1, 1), p + Point(1, -1), p - Point(1, -1)))
        if (!shape.contains(consideredPoint))
          buff += consideredPoint

    buff.toSet.toList
  }

  protected def getSignificantPointsOfPerimeter(perimeter: List[Point]): List[Point] = {
    val buff = perimeter.to[collection.mutable.ListBuffer]

    for (p <- perimeter) {
      if (perimeter.contains(p + Point(1, 0)) && perimeter.contains(p - Point(1, 0)))
        buff -= p
      if (perimeter.contains(p + Point(0, 1)) && perimeter.contains(p - Point(0, 1)))
        buff -= p
    }
    buff.toList
  }
}
