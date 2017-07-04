package Abstract2D.Objects

import Abstract2D.MapObject
import Ancillary.Point

class Signpost(signpoint: Point, entrypoint: Point) extends MapObject {
  override val pointsList: List[Point] = List(signpoint)
  override val hardness: Int = 1
  override val entryPoint: Option[Point] = Option(entrypoint)
}
