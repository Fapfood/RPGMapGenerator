package Abstract3D.Shifters

import Ancillary.Point

class NormalShifter(mapWidth: Int, mapHeight: Int) extends Shifter(mapWidth, mapHeight) {
  override def apply(point: Point): Point = point
}