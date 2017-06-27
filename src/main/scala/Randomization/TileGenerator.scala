package Randomization

import Abstract3D.{PieceOfMap, PieceOfPath, PieceOfSign, PieceOfTree}
import Ancillary.Point
import OutsideTiles.DirectTiles.{BroadTree, NarrowTree, Signpost}
import Randomization.OutsideTiles.FoldingComplexTiles.Road
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

        case _ =>
      }

    buff.toList
  }
}
