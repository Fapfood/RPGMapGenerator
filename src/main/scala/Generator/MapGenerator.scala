package Generator

import Abstract2D.Abstract2DMapGenerator
import Abstract3D.Abstract3DMapGenerator
import GUI.MapParameters
import Randomization.TileGenerator
import com.sksamuel.scrimage.Image


object MapGenerator {
  def createMapWithParameters(mapParams: MapParameters): Image = {
    val map = new Abstract2DMapGenerator(mapParams).generateMap
    val piecesOfMap = new Abstract3DMapGenerator(map).generateMap()
    val piecesOfTile = new TileGenerator(piecesOfMap).generateMap()
    val image = new ImageCreator(mapParams.width, mapParams.height).addTiles(piecesOfTile)
    image
  }
}
