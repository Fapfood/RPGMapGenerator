package Abstract2D.Containers

import Abstract2D.Ancillary.MapObject
import Abstract2D.Objects.Ground
import Ancillary.Point

class AllGround(grounds: List[Ground]) extends MapObject {
  override val pointsList: List[Point] = grounds.flatten.distinct
  override val hardness: Int = grounds.head.hardness
}
