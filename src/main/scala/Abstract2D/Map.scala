package Abstract2D

import Abstract2D.Ancillary.{MapObject, RectangularObject}
import _root_.Ancillary.Point

class Map(val x: Int, val y: Int) {
  private val elems = collection.mutable.ListBuffer.empty[MapObject]
  val pointsList: List[Point] = new RectangularObject {}.buildBase(Point(), Point(x - 1, y - 1))

  def addElement(elem: MapObject): Unit = elems += elem

  def getElements: List[MapObject] = elems.toList

  def getTopLeftCorner: Point = Point(0, 0)

  def getBottomRightCorner: Point = Point(x, y)

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
