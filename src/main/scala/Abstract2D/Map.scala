package Abstract2D

import Ancillary.Point

class Map(val x: Int, val y: Int) {
  val list: List[MapObject] = List()

  def isHardOccupied(point: Point): Boolean = {
    for (o <- list)
      if (o.getLevelOfOccupancy(point) > 0)
        return true

    false
  }

}
