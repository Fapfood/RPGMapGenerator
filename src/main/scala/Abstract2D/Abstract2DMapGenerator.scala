package Abstract2D

import Abstract2D.Abstract2DTileType.Abstract2DTileType

class Abstract2DMapGenerator(val MapParams : MapParameters) {
  def generateMap(): Array[Array[Abstract2DTileType]] = {
    var map = Array.ofDim[Abstract2DTileType](MapParams.width, MapParams.height)
    map
  }
}
