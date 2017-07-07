package Generators

import Abstract2D.DTO_Map
import Abstract2D.Objects._
import Abstract3D.PiecesOfMap._
import Abstract3D.{Matrix, PieceOfMap}
import Ancillary.{MatrixGetter, Point}
import Base._

class PieceOfMapGenerator(map: DTO_Map, base: Base) {
  private val buff = collection.mutable.ListBuffer.empty[PieceOfMap]
  private var id = 0

  def generateMap: List[PieceOfMap] = {
    generateBase()
    generateLayer1()
    generateLayer2()
    buff.toList
  }

  private def generateBase(): Unit = {
    val path_id = giveId
    val path_points = base.getElements.filter(e => e._2 == BaseType.Path).map(e => e._1)
    for (tuple <- MatrixGetter.fromList(path_points))
      buff += PieceOfPath(path_id, tuple._1, tuple._2, 0)

    val ground_id = giveId
    val ground_points = base.getElements.filter(e => e._2 == BaseType.Ground).map(e => e._1)
    for (tuple <- MatrixGetter.fromList(ground_points))
      buff += PieceOfGround(ground_id, tuple._1, tuple._2, 0)

    val grass_id = giveId
    val grass_points = base.getElements.filter(e => e._2 == BaseType.Grass).map(e => e._1)
    for (tuple <- MatrixGetter.fromList(grass_points))
      buff += PieceOfGrass(grass_id, tuple._1, tuple._2, 0)
  }

  private def generateLayer1(): Unit = {
    for (el <- map.getElements)
      el match {
        case tree: Tree =>
          val x_min = tree.pointsList.map(_.x).min
          val y_min = tree.pointsList.map(_.y).min - 1
          // bo korona drzewa
          val x_max = tree.pointsList.map(_.x).max
          val y_max = tree.pointsList.map(_.y).max

          val id = giveId
          for (point <- tree.pointsList) {
            val rootMatrix = Array.fill(y_max - y_min + 1, x_max - x_min + 1)(false)
            rootMatrix(point.y - y_min)(point.x - x_min) = true

            val branchMatrix = Array.fill(y_max - y_min + 1, x_max - x_min + 1)(false)
            branchMatrix(point.y - y_min - 1)(point.x - x_min) = true

            buff += PieceOfTree(id, point, Matrix(rootMatrix), 1)
            buff += PieceOfTree(id, point - Point(0, 1), Matrix(branchMatrix), 2)
          }

        case signpost: Signpost =>
          buff += PieceOfSign(giveId, signpost.pointsList.head, Matrix(true), 1)

        case building: Building =>
          val points = collection.mutable.ListBuffer.empty[Point]

          for (x <- building.topLeft.x to building.bottomRight.x)
            for (y <- building.bottomRight.y - building.heightOfStorey * building.numberOfStoreys + 1
              to building.bottomRight.y)
              points += Point(x, y)

          val id = giveId
          for (tuple <- MatrixGetter.fromList(points.toList))
            buff += PieceOfWall(id, tuple._1, tuple._2, 1)

        case _ =>
      }
  }

  private def generateLayer2(): Unit = {
    for (el <- map.getElements)
      el match {
        case building: Building =>
          val id = giveId
          for (tuple <- MatrixGetter.fromList(building.pointsList))
            buff += PieceOfRoof(id, tuple._1 - Point(0, building.heightOfStorey * building.numberOfStoreys), tuple._2, 3)

          // door
          if (building.entryPoint.nonEmpty && building.bottomRight.y + 1 == building.entryPoint.get.y) {
            val id = giveId
            val doorBottomMatrix = Array.fill(2, 1)(false)
            doorBottomMatrix(1)(0) = true
            val doorTopMatrix = Array.fill(2, 1)(false)
            doorTopMatrix(0)(0) = true
            buff += PieceOfDoor(id, building.entryPoint.get - Point(0, 1), Matrix(doorBottomMatrix), 2)
            buff += PieceOfDoor(id, building.entryPoint.get - Point(0, 2), Matrix(doorTopMatrix), 2)
          }

        case _ =>
      }
  }

  private def giveId: Int = {
    id += 1
    id
  }
}