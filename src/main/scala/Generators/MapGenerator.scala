package Generators

import GUI.MapParameters
import com.sksamuel.scrimage.Image


object MapGenerator {
  def createMapWithParameters(mapParams: MapParameters): Image = {
    println(">> generating 2D map")
    val map = new Abstract2DMapGenerator(mapParams).generateMap
    println(">> generating base for the map")
    val baseMap = new BaseGenerator(map).generateBase
    println(">> generating arrays of elements")
    val piecesOfMap = new PieceOfMapGenerator(map, baseMap).generateMap
    println(">> generating tiles")
    val piecesOfTile = new TileGenerator(piecesOfMap).generateMap
    println(">> generating image")
    val image = new ImageCreator(mapParams.width, mapParams.height).addTiles(piecesOfTile)
    image
  }
}
