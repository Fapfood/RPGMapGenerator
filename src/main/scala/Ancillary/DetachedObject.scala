package Ancillary

trait DetachedObject {
  val pointsList: List[Point] = List()
  val hardness: Int = 0

  def getLevelOfOccupancy(point: Point): Int = {
    if (pointsList.contains(point)) hardness
    else 0
  }
}