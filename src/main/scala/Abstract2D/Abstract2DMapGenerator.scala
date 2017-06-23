package Abstract2D

import Ancillary.Point

class Abstract2DMapGenerator(val MapParams: MapParameters) {

  def generateMock(): Map = {
    val map = new Map(5, 5)
    map.addElement(new Tree(List(Point(0, 0))))
    map.addElement(new Tree(List(Point(1, 0))))
    map.addElement(new Tree(List(Point(0, 1))))
    map.addElement(new Tree(List(Point(1, 1))))
    map
  }
}
