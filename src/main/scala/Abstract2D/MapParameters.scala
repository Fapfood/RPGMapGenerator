package Abstract2D

class MapParameters private(var width: Int = 0, var height: Int = 0, var numberOfTrees: Int = 0, var numberOfHouses: Int = 0) {

}

object MapParameters{
  private val _instance = new MapParameters()
  def instance() = _instance
}