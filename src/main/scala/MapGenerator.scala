class MapGenerator(val MapParams : MapParameters) {
  def generateMap(): Array[Array[MapField]] = {
    var map = Array.ofDim[MapField](MapParams.width, MapParams.height)
    map
  }
}
