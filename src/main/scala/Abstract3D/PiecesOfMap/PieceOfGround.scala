package Abstract3D.PiecesOfMap

import Abstract3D.{Matrix, PieceOfMap}
import Ancillary.Point

case class PieceOfGround(objectId: Int, point: Point, mapNxM: Matrix, layer: Int)
  extends PieceOfMap(objectId, point, mapNxM, layer)