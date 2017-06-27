package Abstract2DObjects

import Abstract2DAncillary.Point

class Signpost(signpoint: Point, entrypoint: Point) extends MapObject {
  override protected val pointsList: List[Point] = List(signpoint)
  override protected val hardness: Int = 1
  override protected val entryPoint: Option[Point] = Option(entrypoint)
}
