package Generator

import java.io.File

import com.sksamuel.scrimage.{Image, Position}

sealed abstract class TileBase(tilesetName: String) {
  protected val TILE_SIDE = 32
  protected val empty: Image = Image.fromFile(new File("resources\\Empty.png"))
  protected val image: Image = Image.fromFile(new File("resources\\tilesets\\" + tilesetName))

  def getTileFor(map3x3: Array[Array[Boolean]]): Image = empty
}

case class DirectTile(tilesetName: String, x_start: Int, x_end: Int, y_start: Int, y_end: Int)
  extends TileBase(tilesetName) {

  override def getTileFor(mapNxM: Array[Array[Boolean]]): Image = {
    for (x <- mapNxM.indices)
      for (y <- mapNxM(0).indices)
        if (mapNxM(x)(y))
          return image.translate(-TILE_SIDE * (x_start + x), -TILE_SIDE * (y_start + y))
            .resizeTo(TILE_SIDE, TILE_SIDE, Position.TopLeft)
    empty
  }
}

class FoldingStraightTile(override val tilesetName: String, override val x_start: Int, override val y_start: Int)
  extends FoldingComplexTile(tilesetName, x_start, y_start) {
  override val x_offset = 0
  override val y_offset = 0

  override def getTileFor(map3x3: Array[Array[Boolean]]): Image = {
    var img = empty
    if (!map3x3(1)(1)) return img

    //Position.TopLeft
    if (map3x3(1)(0) && map3x3(0)(1)) img = img.overlay(getStraightPart(10))
    else if (map3x3(1)(0)) img = img.overlay(getStraightPart(8))
    else if (map3x3(0)(1)) img = img.overlay(getStraightPart(2))
    else img = img.overlay(getStraightPart(0))

    //Position.TopRight
    if (map3x3(1)(0) && map3x3(2)(1)) img = img.overlay(getStraightPart(9))
    else if (map3x3(1)(0)) img = img.overlay(getStraightPart(11))
    else if (map3x3(2)(1)) img = img.overlay(getStraightPart(1))
    else img = img.overlay(getStraightPart(3))

    //Position.BottomLeft
    if (map3x3(1)(2) && map3x3(0)(1)) img = img.overlay(getStraightPart(6))
    else if (map3x3(1)(2)) img = img.overlay(getStraightPart(4))
    else if (map3x3(0)(1)) img = img.overlay(getStraightPart(14))
    else img = img.overlay(getStraightPart(12))

    //Position.BottomRight
    if (map3x3(1)(2) && map3x3(2)(1)) img = img.overlay(getStraightPart(5))
    else if (map3x3(1)(2)) img = img.overlay(getStraightPart(7))
    else if (map3x3(2)(1)) img = img.overlay(getStraightPart(13))
    else img = img.overlay(getStraightPart(15))

    img
  }
}

case class FoldingComplexTile(tilesetName: String, x_start: Int, y_start: Int)
  extends TileBase(tilesetName) {
  val x_offset = 0
  val y_offset = 1

  protected def getStraightPart(i: Int): Image = {
    val HALF_SIDE = TILE_SIDE / 2
    val img = image.translate(-TILE_SIDE * (x_start + x_offset), -TILE_SIDE * (y_start + y_offset))
    val position =
      if (List(0, 2, 8, 10).contains(i)) Position.TopLeft
      else if (List(1, 3, 9, 11).contains(i)) Position.TopRight
      else if (List(4, 6, 12, 14).contains(i)) Position.BottomLeft
      else Position.BottomRight
    img.translate(-HALF_SIDE * (i % 4), -HALF_SIDE * (i / 4))
      .resizeTo(HALF_SIDE, HALF_SIDE, Position.TopLeft)
      .resizeTo(TILE_SIDE, TILE_SIDE, position)
  }

  private def getSpecialPart(i: Int): Image = {
    val HALF_SIDE = TILE_SIDE / 2
    val img = image.translate(-TILE_SIDE * (x_start + 1), -TILE_SIDE * y_start)
    val position =
      if (i == 0) Position.TopLeft
      else if (i == 1) Position.TopRight
      else if (i == 2) Position.BottomLeft
      else Position.BottomRight
    img.translate(-HALF_SIDE * (i % 2), -HALF_SIDE * (i / 2))
      .resizeTo(HALF_SIDE, HALF_SIDE, Position.TopLeft)
      .resizeTo(TILE_SIDE, TILE_SIDE, position)
  }

  override def getTileFor(map3x3: Array[Array[Boolean]]): Image = {
    var img = empty
    if (!map3x3(1)(1)) return img

    //Position.TopLeft
    if (!map3x3(0)(0) && map3x3(1)(0) && map3x3(0)(1)) img = img.overlay(getSpecialPart(0))
    else if (map3x3(1)(0) && map3x3(0)(1)) img = img.overlay(getStraightPart(10))
    else if (map3x3(1)(0)) img = img.overlay(getStraightPart(8))
    else if (map3x3(0)(1)) img = img.overlay(getStraightPart(2))
    else img = img.overlay(getStraightPart(0))

    //Position.TopRight
    if (!map3x3(2)(0) && map3x3(1)(0) && map3x3(2)(1)) img = img.overlay(getSpecialPart(1))
    else if (map3x3(1)(0) && map3x3(2)(1)) img = img.overlay(getStraightPart(9))
    else if (map3x3(1)(0)) img = img.overlay(getStraightPart(11))
    else if (map3x3(2)(1)) img = img.overlay(getStraightPart(1))
    else img = img.overlay(getStraightPart(3))

    //Position.BottomLeft
    if (!map3x3(0)(2) && map3x3(1)(2) && map3x3(0)(1)) img = img.overlay(getSpecialPart(2))
    else if (map3x3(1)(2) && map3x3(0)(1)) img = img.overlay(getStraightPart(6))
    else if (map3x3(1)(2)) img = img.overlay(getStraightPart(4))
    else if (map3x3(0)(1)) img = img.overlay(getStraightPart(14))
    else img = img.overlay(getStraightPart(12))

    //Position.BottomRight
    if (!map3x3(2)(2) && map3x3(1)(2) && map3x3(2)(1)) img = img.overlay(getSpecialPart(3))
    else if (map3x3(1)(2) && map3x3(2)(1)) img = img.overlay(getStraightPart(5))
    else if (map3x3(1)(2)) img = img.overlay(getStraightPart(7))
    else if (map3x3(2)(1)) img = img.overlay(getStraightPart(13))
    else img = img.overlay(getStraightPart(15))

    img
  }
}