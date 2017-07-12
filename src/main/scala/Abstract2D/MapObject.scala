package Abstract2D

import _root_.Ancillary.Point

trait MapObject {
  val pointsList: List[Point]
  val hardness: Int
  val id: String = this.toString
  val entryPoint: Option[Point] = None

  def getLevelOfOccupancy(point: Point): Int = {
    if (pointsList.contains(point)) hardness
    else 0
  }
}