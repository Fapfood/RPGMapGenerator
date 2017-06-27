package Generator

import java.io.File

import Abstract2DAncillary.Point
import com.sksamuel.scrimage.{Image, Position}

class Generator(x: Int, y: Int) {
  private val TILE_SIDE = 32
  var image: Image = makeImage(x, y)

  def addTileOn(tile: Image, position: Point): Unit = {
    image = image.overlay(tile, TILE_SIDE * position.x, TILE_SIDE * position.y)
  }

  private def makeImage(x: Int, y: Int): Image = {
    val in = new File("resources\\Empty.png")
    val image = Image.fromFile(in)
    image.resizeTo(TILE_SIDE * x, TILE_SIDE * y, Position.TopLeft)
  }
}
