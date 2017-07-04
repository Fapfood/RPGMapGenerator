package Generators

import java.io.File

import Ancillary.Point
import Randomization.Tile
import com.sksamuel.scrimage.{Image, Position}

class ImageCreator(x: Int, y: Int) {
  private val TILE_SIDE = 32
  private var image: Image = makeImage(x, y)

  def addTiles(tuples: List[Tile]): Image = {
    val buff = tuples.to[collection.mutable.ListBuffer]
    while (buff.nonEmpty) {
      val priority = buff.map(v => v.layer).min
      val withThisPriority = buff.filter(v => v.layer == priority)
      buff --= withThisPriority
      for (b <- withThisPriority)
        addTileOn(b.tile, b.point)
    }
    image
  }

  private def addTileOn(tile: Image, position: Point): Unit = {
    image = image.overlay(tile, TILE_SIDE * position.x, TILE_SIDE * position.y)
  }

  private def makeImage(x: Int, y: Int): Image = {
    val in = new File("src\\main\\resources\\Empty.png")
    val image = Image.fromFile(in)
    image.resizeTo(TILE_SIDE * x, TILE_SIDE * y, Position.TopLeft)
  }
}
