package Generators

import Abstract2D.DTO_Map
import Ancillary.Point
import Base._

class BaseGenerator(map: DTO_Map) {

  def generateBase: DTO_Base = {
    val base = new DTO_Base(map.x, map.y)

    val grass = new Grass(map.getTopLeftCorner, map.getBottomRightCorner)
    base ++= (grass.pointsList, BaseType.Grass)

    val ground = new Ground(Point(10, 10), 30)
    base ++= (ground.pointsList.filter(map.belongsToMap), BaseType.Ground)

    val path = new AllPaths(map)
    base ++= (path.pointsList, BaseType.Path)

    base
  }
}