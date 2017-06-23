package Abstract2D

import Ancillary.Point

trait MapObject {
  def getLevelOfOccupancy(point: Point): Int
}