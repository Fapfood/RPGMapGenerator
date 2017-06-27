package Abstract2DObjects

import Abstract2DAncillary.{Perimeter, Point}

class Map(val x: Int, val y: Int) extends Perimeter {
  private val elems = collection.mutable.ListBuffer.empty[MapObject]

  def addElement(elem: MapObject): Unit = elems += elem

  def getElements: List[MapObject] = elems.toList

  def isHardOccupied(point: Point): Boolean = {
    for (o <- elems)
      if (o.getLevelOfOccupancy(point) > 0)
        return true

    false
  }

  def getGroupOfHardPointsConnectedWith(point: Point): List[Point] = {
    if (!isHardOccupied(point))
      throw new Exception("Wrong point list in getGroupOfHardPointsConnectedWith")

    @scala.annotation.tailrec
    def group(points: List[Point]): List[Point] = {

      val buff = points.to[collection.mutable.ListBuffer]

      for (p <- getPerimeter(points)) {
        if (isHardOccupied(p))
          buff += p
      }

      if (buff.length == points.length)
        return points

      group(buff.toList)
    }

    group(List(point))
  }
}
