package Ancillary

object GroupOfArea {
  def getGroupsOfConnectedPoints(points: List[Point]): List[Set[Point]] = {
    val buff = collection.mutable.ListBuffer.empty[Set[Point]]
    for (p <- points)
      buff += group(Set(p))

    @scala.annotation.tailrec
    def group(pointSet: Set[Point]): Set[Point] = {

      val buff = pointSet.to[collection.mutable.ListBuffer]

      for (p <- Perimeter.getPerimeter(pointSet.toList)) {
        if (points.contains(p))
          buff += p
      }

      if (buff.length == pointSet.size)
        return pointSet

      group(buff.toSet)
    }

    buff.toSet.toList
  }
}