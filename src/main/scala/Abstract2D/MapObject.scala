package Abstract2D

import Ancillary.Point

trait MapObject {
  abstract def getLevelOfOccupancy(point: Point): Int
}