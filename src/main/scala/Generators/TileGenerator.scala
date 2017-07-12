package Generators

import Abstract3D.PiecesOfMap._
import Abstract3D._
import Randomization.Tiles.OutsideTiles.DirectTiles._
import Randomization.Tiles.OutsideTiles.FoldingComplexTiles.{Grass, Ground, Road}
import Randomization.Tiles.OutsideTiles.FoldingStraightTiles.{NarrowBoardsWall, StrawRoof, StrawRoof2, WoodenStumpsWall}
import Randomization.{DTO_Tile, Memorizer}

import scala.util.Random

class TileGenerator(piecesOfMap: List[PieceOfMap]) {

  def generateMap(memorizer: Memorizer): List[DTO_Tile] = {
    val buff = collection.mutable.ListBuffer.empty[DTO_Tile]

    for (pieceOfMap <- piecesOfMap)
      pieceOfMap match {
        case piece: PieceOfTree =>
          if (piece.mapNxM.length == 2) {
            val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
            else Random.nextInt(3)
            memorizer.memoization += (piece.objectId -> randomize)
            randomize match {
              case 0 => buff += new DTO_Tile(piece.point, NarrowDeadTree.getTileFor(piece.mapNxM), piece.layer)
              case 1 => buff += new DTO_Tile(piece.point, NarrowTree2.getTileFor(piece.mapNxM), piece.layer)
              case 2 => buff += new DTO_Tile(piece.point, NarrowTree.getTileFor(piece.mapNxM), piece.layer)
            }
          }
          if (piece.mapNxM.length == 4) {
            val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
            else Random.nextInt(4)
            memorizer.memoization += (piece.objectId -> randomize)
            randomize match {
              case 0 => buff += new DTO_Tile(piece.point, BroadTree.getTileFor(piece.mapNxM), piece.layer)
              case 1 => buff += new DTO_Tile(piece.point, BroadConifer.getTileFor(piece.mapNxM), piece.layer)
              case 2 => buff += new DTO_Tile(piece.point, BroadTree2.getTileFor(piece.mapNxM), piece.layer)
              case 3 => buff += new DTO_Tile(piece.point, BroadDeadTree.getTileFor(piece.mapNxM), piece.layer)
            }
          }

        case piece: PieceOfPath =>
          val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
          else Random.nextInt(1)
          memorizer.memoization += (piece.objectId -> randomize)
          randomize match {
            case 0 => buff += new DTO_Tile(piece.point, Road.getTileFor(piece.mapNxM), piece.layer)
          }

        case piece: PieceOfSign =>
          val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
          else Random.nextInt(2)
          memorizer.memoization += (piece.objectId -> randomize)
          randomize match {
            case 0 => buff += new DTO_Tile(piece.point, Signpost.getTileFor(piece.mapNxM), piece.layer)
            case 1 => buff += new DTO_Tile(piece.point, Signpost2.getTileFor(piece.mapNxM), piece.layer)
          }

        case piece: PieceOfGrass =>
          val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
          else Random.nextInt(1)
          memorizer.memoization += (piece.objectId -> randomize)
          randomize match {
            case 0 => buff += new DTO_Tile(piece.point, Grass.getTileFor(piece.mapNxM), piece.layer)
          }

        case piece: PieceOfGround =>
          val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
          else Random.nextInt(1)
          memorizer.memoization += (piece.objectId -> randomize)
          randomize match {
            case 0 => buff += new DTO_Tile(piece.point, Ground.getTileFor(piece.mapNxM), piece.layer)
          }

        case piece: PieceOfWall =>
          val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
          else Random.nextInt(2)
          memorizer.memoization += (piece.objectId -> randomize)
          randomize match {
            case 0 => buff += new DTO_Tile(piece.point, NarrowBoardsWall.getTileFor(piece.mapNxM), piece.layer)
            case 1 => buff += new DTO_Tile(piece.point, WoodenStumpsWall.getTileFor(piece.mapNxM), piece.layer)
          }

        case piece: PieceOfRoof =>
          val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
          else Random.nextInt(2)
          memorizer.memoization += (piece.objectId -> randomize)
          randomize match {
            case 0 => buff += new DTO_Tile(piece.point, StrawRoof.getTileFor(piece.mapNxM), piece.layer)
            case 1 => buff += new DTO_Tile(piece.point, StrawRoof2.getTileFor(piece.mapNxM), piece.layer)
          }

        case piece: PieceOfDoor =>
          val randomize = if (memorizer.memoization.contains(piece.objectId)) memorizer.memoization(piece.objectId)
          else Random.nextInt(1)
          memorizer.memoization += (piece.objectId -> randomize)
          randomize match {
            case 0 => buff += new DTO_Tile(piece.point, Door.getTileFor(piece.mapNxM), piece.layer)
          }

        case _ =>
      }

    buff.toList
  }
}