package Abstract2D.Objects

import Abstract2D.Ancillary.{MapObject, RectangularObject}
import Ancillary.Point

class Grass(leftTop: Point, rightBottom: Point) extends MapObject with RectangularObject {
  override val pointsList: List[Point] = buildBase(leftTop, rightBottom)
  override val hardness: Int = 0
}
