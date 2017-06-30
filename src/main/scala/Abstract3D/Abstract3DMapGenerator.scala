package Abstract3D

import Abstract2D.Base._
import Abstract2D.Map
import Abstract2D.Objects._
import Abstract3D.PiecesOfMap._
import Ancillary.{Perimeter, Point}

import scala.util.Random

class Abstract3DMapGenerator(val map: Map) {
  private val buff = collection.mutable.ListBuffer.empty[PieceOfMap]

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
          for (tuple <- getTypicalTuples(path.pointsList))
            buff += PieceOfPath(tuple._1, 0, tuple._2, 0)

        case ground: AllGround =>
          for (tuple <- getTypicalTuples(ground.pointsList))
            buff += PieceOfGround(tuple._1, 0, tuple._2, 0)

        case grass: AllGrass =>
          for (tuple <- getTypicalTuples(grass.pointsList))
            buff += PieceOfGrass(tuple._1, 0, tuple._2, 0)

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

          val rand = Random.nextInt(4)
          for (point <- tree.pointsList) {
            val rootMatrix = Array.fill(x_max - x_min + 1, y_max - y_min + 1)(false)
            rootMatrix(point.x - x_min)(point.y - y_min) = true

            val branchMatrix = Array.fill(x_max - x_min + 1, y_max - y_min + 1)(false)
            branchMatrix(point.x - x_min)(point.y - y_min - 1) = true

            buff += PieceOfTree(point, 1, rootMatrix, rand)
            buff += PieceOfTree(point - Point(0, 1), 2, branchMatrix, rand)
          }

        case signpost: Signpost =>
          val rand = Random.nextInt(2)
          buff += PieceOfSign(signpost.pointsList.head, 1, Array.fill(1, 1)(true), rand)

        case building: Building =>
          val points = collection.mutable.ListBuffer.empty[Point]

          for (x <- building.topLeft.x to building.bottomRight.x)
            for (y <- building.bottomRight.y - building.heightOfStorey * building.numberOfStoreys + 1
              to building.bottomRight.y)
              points += Point(x, y)

          val rand = Random.nextInt(2)
          for (tuple <- getTypicalTuples(points.toList))
            buff += PieceOfWall(tuple._1, 1, tuple._2, rand)

        case _ =>
      }
  }

  private def generateLayer2(): Unit = {
    for (el <- map.getElements)
      el match {
        case building: Building =>
          val rand = Random.nextInt(2)
          for (tuple <- getTypicalTuples(building.pointsList))
            buff += PieceOfRoof(tuple._1 - Point(0, building.heightOfStorey * building.numberOfStoreys), 3, tuple._2, rand)

          // door
          if (building.entryPoint.nonEmpty && building.bottomRight.y + 1 == building.entryPoint.get.y) {
            val doorBottomMatrix = Array.fill(1, 2)(false)
            doorBottomMatrix(0)(1) = true
            val doorTopMatrix = Array.fill(1, 2)(false)
            doorTopMatrix(0)(0) = true
            buff += PieceOfDoor(building.entryPoint.get - Point(0, 1), 2, doorBottomMatrix, 0)
            buff += PieceOfDoor(building.entryPoint.get - Point(0, 2), 2, doorTopMatrix, 0)
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

  private def getTypicalTuples(points: List[Point]): List[(Point, Array[Array[Boolean]])] = {
    val buff = collection.mutable.ListBuffer.empty[(Point, Array[Array[Boolean]])]

    for (point <- points) {
      val matrix = Array.fill(3, 3)(false)
      matrix(1)(1) = true

      for (p <- Perimeter.getPerimeter(List(point)))
        if (points.contains(p))
          matrix(1 - (point - p).x)(1 - (point - p).y) = true
      buff += Tuple2(point, matrix)
    }
    buff.toList
  }
}
