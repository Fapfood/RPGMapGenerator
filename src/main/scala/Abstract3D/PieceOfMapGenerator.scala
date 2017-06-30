package Abstract3D

import Abstract2D.Base._
import Abstract2D.Map
import Abstract2D.Objects._
import Abstract3D.PiecesOfMap._
import Ancillary.{ArrayGetter, Point}

class PieceOfMapGenerator(map: Map) {
  private val buff = collection.mutable.ListBuffer.empty[PieceOfMap]
  private var id = 0

  def generateMap: List[PieceOfMap] = {
    generateLayer0()
    generateLayer1()
    generateLayer2()
    generateLayer3()
    buff.toList
  }

  private def generateLayer0(): Unit = {
    for (el <- map.getElements)
      el match {
        case path: AllPaths =>
          val id = giveId
          for (tuple <- ArrayGetter.fromList(path.pointsList))
            buff += PieceOfPath(id, tuple._1, tuple._2, 0)

        case ground: AllGround =>
          val id = giveId
          for (tuple <- ArrayGetter.fromList(ground.pointsList))
            buff += PieceOfGround(id, tuple._1, tuple._2, 0)

        case grass: AllGrass =>
          val id = giveId
          for (tuple <- ArrayGetter.fromList(grass.pointsList))
            buff += PieceOfGrass(id, tuple._1, tuple._2, 0)

        case _ =>
      }
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
            val rootMatrix = Array.fill(x_max - x_min + 1, y_max - y_min + 1)(false)
            rootMatrix(point.x - x_min)(point.y - y_min) = true

            val branchMatrix = Array.fill(x_max - x_min + 1, y_max - y_min + 1)(false)
            branchMatrix(point.x - x_min)(point.y - y_min - 1) = true

            buff += PieceOfTree(id, point, rootMatrix, 1)
            buff += PieceOfTree(id, point - Point(0, 1), branchMatrix, 2)
          }

        case signpost: Signpost =>
          buff += PieceOfSign(giveId, signpost.pointsList.head, Array.fill(1, 1)(true), 1)

        case building: Building =>
          val points = collection.mutable.ListBuffer.empty[Point]

          for (x <- building.topLeft.x to building.bottomRight.x)
            for (y <- building.bottomRight.y - building.heightOfStorey * building.numberOfStoreys + 1
              to building.bottomRight.y)
              points += Point(x, y)

          val id = giveId
          for (tuple <- ArrayGetter.fromList(points.toList))
            buff += PieceOfWall(id, tuple._1, tuple._2, 1)

        case _ =>
      }
  }

  private def generateLayer2(): Unit = {
    for (el <- map.getElements)
      el match {
        case building: Building =>
          val id = giveId
          for (tuple <- ArrayGetter.fromList(building.pointsList))
            buff += PieceOfRoof(id, tuple._1 - Point(0, building.heightOfStorey * building.numberOfStoreys), tuple._2, 3)

          // door
          if (building.entryPoint.nonEmpty && building.bottomRight.y + 1 == building.entryPoint.get.y) {
            val id = giveId
            val doorBottomMatrix = Array.fill(1, 2)(false)
            doorBottomMatrix(0)(1) = true
            val doorTopMatrix = Array.fill(1, 2)(false)
            doorTopMatrix(0)(0) = true
            buff += PieceOfDoor(id, building.entryPoint.get - Point(0, 1), doorBottomMatrix, 2)
            buff += PieceOfDoor(id, building.entryPoint.get - Point(0, 2), doorTopMatrix, 2)
          }

        case _ =>
      }
  }

  private def generateLayer3(): Unit = {
    for (el <- map.getElements)
      el match {
        case _ =>
      }
  }

  private def giveId: Int = {
    id += 1
    id
  }
}
