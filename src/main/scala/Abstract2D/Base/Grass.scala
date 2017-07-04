package Abstract2D.Base

import Abstract2D.Ancillary.RectangularObject
import Abstract2D.MapObject
import Ancillary.Point

class Grass(leftTop: Point, rightBottom: Point) extends MapObject {
  override val pointsList: List[Point] = RectangularObject.buildBase(leftTop, rightBottom)
  override val hardness: Int = 0
}
