package Base

import Abstract2D.MapObject
import Ancillary.Point

class AllGrass(grass: Grass, allGround: AllGround) extends MapObject {
  override val pointsList: List[Point] = getReduced
  override val hardness: Int = grass.hardness

  def getReduced: List[Point] = {
    val buff = grass.pointsList.to[collection.mutable.ListBuffer]
    buff --= allGround.pointsList
    buff.toList
  }
}
