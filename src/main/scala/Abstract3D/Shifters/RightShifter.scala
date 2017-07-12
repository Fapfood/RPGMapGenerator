package Abstract3D.Shifters

import Ancillary.Point

class RightShifter(mapWidth: Int, mapHeight: Int) extends Shifter(mapWidth, mapHeight) {
  override def apply(point: Point): Point = Point(mapHeight - 1 - point.y, point.x)
}