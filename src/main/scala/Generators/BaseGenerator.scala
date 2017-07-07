package Generators

import Abstract2D.Map
import Base.{AllPaths, Base, BaseType}

class BaseGenerator(map: Map) {

  def generateBase: Base = {
    val base = new Base(map.x, map.y)

    for (point <- new AllPaths(map).pointsList)
      base.add(point, BaseType.Path)

    base
  }
}
