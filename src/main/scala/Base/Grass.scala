package Base

import Abstract2D.MapObject
import Ancillary.{Point, RectangularObject}

class Grass(leftTop: Point, rightBottom: Point) extends MapObject {
  override val pointsList: List[Point] = RectangularObject.buildBase(leftTop, rightBottom)
  override val hardness: Int = 0
}