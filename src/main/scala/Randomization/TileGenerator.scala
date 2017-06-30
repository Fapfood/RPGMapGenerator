package Randomization

import Abstract3D.PiecesOfMap._
import Abstract3D._
import Ancillary.Point
import Randomization.OutsideTiles.DirectTiles._
import Randomization.OutsideTiles.FoldingComplexTiles._
import Randomization.OutsideTiles.FoldingStraightTiles._
import com.sksamuel.scrimage.Image

class TileGenerator(piecesOfMap: List[PieceOfMap]) {

  def generateMap: List[(Point, Int, Image)] = {
    val buff = collection.mutable.ListBuffer.empty[(Point, Int, Image)]

    for (pieceOfMap <- piecesOfMap)
      pieceOfMap match {
        case tree: PieceOfTree =>
          if (tree.mapNxM.flatten.length == 2)
            tree.randomize match {
              case 0 => buff += Tuple3(tree.point, tree.layer, NarrowDeadTree.getTileFor(tree.mapNxM))
              case 1 => buff += Tuple3(tree.point, tree.layer, NarrowTree2.getTileFor(tree.mapNxM))
              case _ => buff += Tuple3(tree.point, tree.layer, NarrowTree.getTileFor(tree.mapNxM))
            }
          if (tree.mapNxM.flatten.length == 4)
            tree.randomize match {
              case 0 => buff += Tuple3(tree.point, tree.layer, BroadTree.getTileFor(tree.mapNxM))
              case 1 => buff += Tuple3(tree.point, tree.layer, BroadConifer.getTileFor(tree.mapNxM))
              case 2 => buff += Tuple3(tree.point, tree.layer, BroadTree2.getTileFor(tree.mapNxM))
              case _ => buff += Tuple3(tree.point, tree.layer, BroadDeadTree.getTileFor(tree.mapNxM))
            }

        case path: PieceOfPath =>
          buff += Tuple3(path.point, path.layer, Road.getTileFor(path.mapNxM))

        case sign: PieceOfSign =>
          sign.randomize match {
            case 0 => buff += Tuple3(sign.point, sign.layer, Signpost.getTileFor(sign.mapNxM))
            case _ => buff += Tuple3(sign.point, sign.layer, Signpost2.getTileFor(sign.mapNxM))
          }

        case grass: PieceOfGrass =>
          buff += Tuple3(grass.point, grass.layer, Grass.getTileFor(grass.mapNxM))

        case ground: PieceOfGround =>
          buff += Tuple3(ground.point, ground.layer, Ground.getTileFor(ground.mapNxM))

        case wall: PieceOfWall =>
          wall.randomize match {
            case 0 => buff += Tuple3(wall.point, wall.layer, NarrowBoardsWall.getTileFor(wall.mapNxM))
            case _ => buff += Tuple3(wall.point, wall.layer, WoodenStumpsWall.getTileFor(wall.mapNxM))
          }

        case roof: PieceOfRoof =>
          roof.randomize match {
            case 0 => buff += Tuple3(roof.point, roof.layer, StrawRoof.getTileFor(roof.mapNxM))
            case _ => buff += Tuple3(roof.point, roof.layer, StrawRoof2.getTileFor(roof.mapNxM))
          }

        case door: PieceOfDoor =>
          buff += Tuple3(door.point, door.layer, Door.getTileFor(door.mapNxM))

        case _ =>
      }

    buff.toList
  }
}
