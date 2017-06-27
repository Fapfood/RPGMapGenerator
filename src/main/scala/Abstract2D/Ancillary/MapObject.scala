package Abstract2D.Ancillary

import Ancillary.Point

trait MapObject {
  val pointsList: List[Point]
  val hardness: Int
  val entryPoint: Option[Point] = None

  def getLevelOfOccupancy(point: Point): Int = {
    if (pointsList.contains(point)) hardness
    else 0
  }
}