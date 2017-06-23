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
}
