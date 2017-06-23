package Ancillary

trait DetachedObject {
  val pointsList: List[Point]
  val hardness: Int

  def getLevelOfOccupancy(point: Point): Int = {
    if (pointsList.contains(point)) hardness
    else 0
  }
}