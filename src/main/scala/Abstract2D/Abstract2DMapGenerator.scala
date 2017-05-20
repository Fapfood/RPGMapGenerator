package Abstract2D

import Abstract2D.Abstract2DTileType.Abstract2DTileType

class Abstract2DMapGenerator(val MapParams : MapParameters) {
  def generateMap(): Array[Array[Abstract2DTileType]] = {
    var map = Array.ofDim[Abstract2DTileType](MapParams.width, MapParams.height)
    map
  }

  def generateMock(): Array[Array[Abstract2DTileType]] = {
    val map = Array.ofDim[Abstract2DTileType](3, 2)
    map(0)(0) = Abstract2DTileType.Ground
    map(0)(1) = Abstract2DTileType.Ground
    map(1)(0) = Abstract2DTileType.Ground
    map(1)(1) = Abstract2DTileType.Ground
    map(2)(0) = Abstract2DTileType.Ground
    map(2)(1) = Abstract2DTileType.Ground
    map
  }
}
