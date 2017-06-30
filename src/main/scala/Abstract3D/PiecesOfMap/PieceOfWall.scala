package Abstract3D.PiecesOfMap

import Abstract3D.PieceOfMap
import Ancillary.Point

case class PieceOfWall(objectId: Int, point: Point, mapNxM: Array[Array[Boolean]], layer: Int)
  extends PieceOfMap(objectId, point, mapNxM, layer)