package Abstract3D.Shifters

import Ancillary.Point

class ReverseShifter(mapWidth: Int, mapHeight: Int) extends Shifter(mapWidth, mapHeight) {
  override def apply(point: Point): Point = Point(mapWidth - 1 - point.x, mapHeight - 1 - point.y)
}