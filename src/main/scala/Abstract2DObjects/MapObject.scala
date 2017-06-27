package Abstract2DObjects

import Abstract2DAncillary.Point

trait MapObject {
  val pointsList: List[Point]
  val hardness: Int

  def getLevelOfOccupancy(point: Point): Int = {
    if (pointsList.contains(point)) hardness
    else 0
  }
}