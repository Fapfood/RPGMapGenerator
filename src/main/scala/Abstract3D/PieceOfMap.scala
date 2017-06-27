package Abstract3D

import Ancillary.Point

sealed abstract class PieceOfMap(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) {}

case class PieceOfTree(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfPath(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfWall(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfRoof(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfDoor(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfSign(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfGrass(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfGround(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)

case class PieceOfFence(point: Point, layer: Int, mapNxM: Array[Array[Boolean]]) extends PieceOfMap(point, layer, mapNxM)