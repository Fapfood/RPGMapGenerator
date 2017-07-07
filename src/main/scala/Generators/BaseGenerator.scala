package Generators

import Abstract2D.DTO_Map
import Base.{AllPaths, Base, BaseType}

class BaseGenerator(map: DTO_Map) {

  def generateBase: Base = {
    val base = new Base(map.x, map.y)

    for (point <- new AllPaths(map).pointsList)
      base.add(point, BaseType.Path)

    base
  }
}