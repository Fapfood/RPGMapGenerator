package Generator

import java.io.File

import Ancillary.Point
import com.sksamuel.scrimage.{Image, Position}

class MapGenerator(x: Int, y: Int) {
  private val TILE_SIDE = 32
  private var image: Image = makeImage(x, y)

  def addTiles(tuples: List[(Point, Int, Image)]): Image = {
    val buff = tuples.to[collection.mutable.ListBuffer]
    while (buff.nonEmpty) {
      val priority = buff.map(v => v._2).min
      val withThisPriority = buff.filter(v => v._2 == priority)
      buff --= withThisPriority
      for (b <- withThisPriority)
        addTileOn(b._3, b._1)
    }
    image
  }

  private def addTileOn(tile: Image, position: Point): Unit = {
    image = image.overlay(tile, TILE_SIDE * position.x, TILE_SIDE * position.y)
  }

  private def makeImage(x: Int, y: Int): Image = {
    val in = new File("resources\\Empty.png")
    val image = Image.fromFile(in)
    image.resizeTo(TILE_SIDE * x, TILE_SIDE * y, Position.TopLeft)
  }
}
