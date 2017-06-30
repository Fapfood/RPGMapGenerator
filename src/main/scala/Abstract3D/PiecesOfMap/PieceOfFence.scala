package Abstract3D.PiecesOfMap

import Abstract3D.PieceOfMap
import Ancillary.Point

case class PieceOfFence(point: Point, layer: Int, mapNxM: Array[Array[Boolean]], randomize: Int)
  extends PieceOfMap(point, layer, mapNxM, randomize)