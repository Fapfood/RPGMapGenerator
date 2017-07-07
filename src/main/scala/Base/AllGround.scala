package Base

import Abstract2D.MapObject
import Ancillary.Point

class AllGround(grounds: List[Ground]) extends MapObject {
  override val pointsList: List[Point] = grounds.flatten(_.entryPoint).distinct
  override val hardness: Int = if (grounds.nonEmpty) grounds.head.hardness else 0
}
