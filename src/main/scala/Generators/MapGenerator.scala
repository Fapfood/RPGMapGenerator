package Generators

import GUI.DTO_MapParameters
import com.sksamuel.scrimage.Image

object MapGenerator {
  def createMapWithParameters(mapParams: DTO_MapParameters): Image = {
    println(">> generating 2D map")
    val map = new Abstract2DMapGenerator(mapParams).generateMap
    println(">> generating base for the map")
    val base = new BaseGenerator(map).generateBase
    println(">> generating arrays of elements")
    val piecesOfMap = new PieceOfMapGenerator(map, base).generateMap
    println(">> generating tiles")
    val tiles = new TileGenerator(piecesOfMap).generateMap
    println(">> generating image")
    val image = new ImageCreator(mapParams.width, mapParams.height).addTiles(tiles)
    image
  }
}