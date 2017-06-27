package Abstract2DObjects

import Abstract2DAncillary.Point

class Tree(listOfRootFields: List[Point]) extends MapObject {
  override val pointsList: List[Point] = listOfRootFields
  override val hardness: Int = 1

  def getRootFields: List[Point] = pointsList
}