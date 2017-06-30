package Abstract2D.Base

import Abstract2D.Ancillary.{FlatObject, MapObject}
import Ancillary.Point

class Ground(midpoint: Point, area: Int) extends MapObject {
  override val pointsList: List[Point] = FlatObject.buildBase(List(midpoint), area)
  override val hardness: Int = 0
}