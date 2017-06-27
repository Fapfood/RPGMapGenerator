package Abstract2DObjects

import Abstract2DAncillary.Point

trait MapObject {
  protected val pointsList: List[Point]
  protected val hardness: Int

  def getLevelOfOccupancy(point: Point): Int = {
    if (pointsList.contains(point)) hardness
    else 0
  }

  def getFields: List[Point] = pointsList
}