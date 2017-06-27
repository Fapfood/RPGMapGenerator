package Abstract2D.Objects

import Abstract2D.Ancillary.MapObject
import Ancillary.Point

class Tree(listOfRootFields: List[Point]) extends MapObject {
  override val pointsList: List[Point] = listOfRootFields
  override val hardness: Int = 1
}