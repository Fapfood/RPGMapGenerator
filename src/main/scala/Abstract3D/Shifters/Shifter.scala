package Abstract3D.Shifters

import Ancillary.Point

abstract class Shifter(mapWidth: Int, mapHeight: Int) {
  def apply(point: Point): Point
}