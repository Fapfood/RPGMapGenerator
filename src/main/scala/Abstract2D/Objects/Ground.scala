package Abstract2D.Objects

import Abstract2D.Ancillary.{FlatObject, MapObject}
import Ancillary.Point

class Ground(midpoint: Point, area: Int) extends MapObject with FlatObject {
  override val pointsList: List[Point] = buildBase(List(midpoint), area)
  override val hardness: Int = 0
}