package Generators

import Abstract2D.Map
import Base._

class BaseGenerator(map: Map) {

  def generateMap: Map = {

    map.addElement(
      new AllGrass(new Grass(map.getTopLeftCorner, map.getBottomRightCorner), new AllGround(List.empty[Ground])))

    map.addElement(new AllPaths(map))

    map

  }

}
