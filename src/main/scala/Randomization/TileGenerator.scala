package Randomization

import Abstract3D._
import Ancillary.Point
import OutsideTiles.DirectTiles._
import OutsideTiles.FoldingComplexTiles._
import OutsideTiles.FoldingStraightTiles._
import com.sksamuel.scrimage.Image

class TileGenerator(piecesOfMap: List[PieceOfMap]) {

  def generateMap(): List[(Point, Int, Image)] = {
    val buff = collection.mutable.ListBuffer.empty[(Point, Int, Image)]

    for (pieceOfMap <- piecesOfMap)
      pieceOfMap match {
        case tree: PieceOfTree =>
          if (tree.mapNxM.flatten.length == 2)
            buff += Tuple3(tree.point, tree.layer, NarrowTree.getTileFor(tree.mapNxM))
          if (tree.mapNxM.flatten.length == 4)
            buff += Tuple3(tree.point, tree.layer, BroadTree.getTileFor(tree.mapNxM))

        case path: PieceOfPath =>
          buff += Tuple3(path.point, path.layer, Road.getTileFor(path.mapNxM))

        case sign: PieceOfSign =>
          buff += Tuple3(sign.point, sign.layer, Signpost.getTileFor(sign.mapNxM))

        case grass: PieceOfGrass =>
          buff += Tuple3(grass.point, grass.layer, Grass.getTileFor(grass.mapNxM))

        case ground: PieceOfGround =>
          buff += Tuple3(ground.point, ground.layer, Ground.getTileFor(ground.mapNxM))

        case wall: PieceOfWall =>
          buff += Tuple3(wall.point, wall.layer, NarrowBoardsWall.getTileFor(wall.mapNxM))

        case roof: PieceOfRoof =>
          buff += Tuple3(roof.point, roof.layer, StrawRoof.getTileFor(roof.mapNxM))

        case door: PieceOfDoor =>
          buff += Tuple3(door.point, door.layer, Door.getTileFor(door.mapNxM))

        case _ =>
      }

    buff.toList
  }
}
