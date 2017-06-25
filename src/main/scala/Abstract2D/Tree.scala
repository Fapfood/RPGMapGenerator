package Abstract2D

import Ancillary.{DetachedObject, Point}

class Tree(listOfRootFields: List[Point]) extends MapObject with DetachedObject {
  override val pointsList: List[Point] = listOfRootFields
  override val hardness: Int = 1

  def getRootFields: List[Point] = pointsList
}