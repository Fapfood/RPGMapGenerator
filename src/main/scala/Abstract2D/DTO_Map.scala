package Abstract2D

import _root_.Ancillary.{Point, RectangularObject}

class DTO_Map(val x: Int, val y: Int) {
  private val elems = collection.mutable.ListBuffer.empty[MapObject]
  val pointsList: List[Point] = RectangularObject.buildBase(getTopLeftCorner, getBottomRightCorner)

  def addElement(elem: MapObject): Unit = elems += elem

  def getElements: List[MapObject] = elems.toList

  def getTopLeftCorner: Point = Point()

  def getBottomRightCorner: Point = Point(x - 1, y - 1)

  def belongsToMap(point: Point): Boolean = pointsList.contains(point)

  def getObstacles: List[Point] = {
    val obstacles = collection.mutable.ListBuffer.empty[Point]
    for (p <- pointsList)
      if (isHardOccupied(p))
        obstacles += p
    obstacles.toList
  }

  def isHardOccupied(point: Point): Boolean = {
    for (o <- elems)
      if (o.getLevelOfOccupancy(point) > 0)
        return true

    false
  }
}