package Generators

import Abstract2D.DTO_Map
import Abstract2D.Objects._
import Abstract3D.PiecesOfMap._
import Abstract3D.Shifters.Shifter
import Abstract3D.{Matrix, PieceOfMap}
import Ancillary.{MatrixGetter, Point}
import Base._

class PieceOfMapGenerator(map: DTO_Map, base: DTO_Base) {
  private val buff = collection.mutable.ListBuffer.empty[PieceOfMap]

  def generateMap(shifter: Shifter): List[PieceOfMap] = {
    generateBase(shifter)
    generateLayer1(shifter)
    generateLayer2(shifter)
    buff.toList
  }

  private def generateBase(shifter: Shifter): Unit = {
    val path_id = "path"
    val path_points = base.getElements.filter(e => e._2 == BaseType.Path).map(e => e._1).map(shifter.apply)
    for (tuple <- MatrixGetter.fromList(path_points))
      buff += PieceOfPath(path_id, tuple._1, tuple._2, 0)

    val ground_id = "ground"
    val ground_points = base.getElements.filter(e => e._2 == BaseType.Ground).map(e => e._1).map(shifter.apply)
    for (tuple <- MatrixGetter.fromList(ground_points))
      buff += PieceOfGround(ground_id, tuple._1, tuple._2, 0)

    val grass_id = "grass"
    val grass_points = base.getElements.filter(e => e._2 == BaseType.Grass).map(e => e._1).map(shifter.apply)
    for (tuple <- MatrixGetter.fromList(grass_points))
      buff += PieceOfGrass(grass_id, tuple._1, tuple._2, 0)
  }

  private def generateLayer1(shifter: Shifter): Unit = {
    for (el <- map.getElements)
      el match {
        case tree: Tree =>
          val tree_points = tree.pointsList.map(shifter.apply)
          val x_min = tree_points.map(_.x).min
          val y_min = tree_points.map(_.y).min - 1
          // bo korona drzewa
          val x_max = tree_points.map(_.x).max
          val y_max = tree_points.map(_.y).max

          for (point <- tree_points) {
            val rootMatrix = Array.fill(y_max - y_min + 1, x_max - x_min + 1)(false)
            rootMatrix(point.y - y_min)(point.x - x_min) = true

            val branchMatrix = Array.fill(y_max - y_min + 1, x_max - x_min + 1)(false)
            branchMatrix(point.y - y_min - 1)(point.x - x_min) = true

            buff += PieceOfTree(tree.id, point, Matrix(rootMatrix), 1)
            buff += PieceOfTree(tree.id, point - Point(0, 1), Matrix(branchMatrix), 2)
          }

        case signpost: Signpost =>
          val sign_point = shifter(signpost.pointsList.head)
          buff += PieceOfSign(signpost.id, sign_point, Matrix(true), 1)

        case building: Building =>
          val points = collection.mutable.ListBuffer.empty[Point]

          val x_min = building.pointsList.map(shifter.apply).map(_.x).min
          val x_max = building.pointsList.map(shifter.apply).map(_.x).max
          val y_max = building.pointsList.map(shifter.apply).map(_.y).max
          val y_min = y_max - building.heightOfStorey * building.numberOfStoreys + 1

          for (x <- x_min to x_max)
            for (y <- y_min to y_max)
              points += Point(x, y)

          val wall_points = points.toList

          for (tuple <- MatrixGetter.fromList(wall_points))
            buff += PieceOfWall(building.id + "wall", tuple._1, tuple._2, 1)

        case _ =>
      }
  }

  private def generateLayer2(shifter: Shifter): Unit = {
    for (el <- map.getElements)
      el match {
        case building: Building =>
          val roof_points = building.pointsList.map(shifter.apply)
          for (tuple <- MatrixGetter.fromList(roof_points))
            buff += PieceOfRoof(
              building.id + "roof", tuple._1 - Point(0, building.heightOfStorey * building.numberOfStoreys), tuple._2, 3)

          // door
          if (building.entryPoint.nonEmpty) {
            val mostBottomHouseCoord = roof_points.map(_.y).max
            val entryPoint = shifter(building.entryPoint.get)
            val isProperSideOfBuilding = mostBottomHouseCoord == entryPoint.y - 1
            if (isProperSideOfBuilding) {
              val doorBottomPoint = entryPoint - Point(0, 1)
              val doorBottomMatrix = Array.fill(2, 1)(false)
              doorBottomMatrix(1)(0) = true
              val doorTopPoint = entryPoint - Point(0, 2)
              val doorTopMatrix = Array.fill(2, 1)(false)
              doorTopMatrix(0)(0) = true
              buff += PieceOfDoor(building.id + "door", doorBottomPoint, Matrix(doorBottomMatrix), 2)
              buff += PieceOfDoor(building.id + "door", doorTopPoint, Matrix(doorTopMatrix), 2)
            }
          }

        case _ =>
      }
  }
}