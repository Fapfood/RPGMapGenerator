package Abstract3D.Shifters

import Ancillary.Point

class LeftShifter(mapWidth: Int, mapHeight: Int) extends Shifter(mapWidth, mapHeight) {
  override def apply(point: Point): Point = Point(point.y, mapWidth - 1 - point.x)
}