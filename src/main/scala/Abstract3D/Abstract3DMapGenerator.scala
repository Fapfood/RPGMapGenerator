package Abstract3D

import Abstract2D.Map
import Abstract2D.Ancillary.MapObject
import Abstract2D.Containers.{AllGrass, AllGround, AllPaths}
import Abstract2D.Objects._
import Ancillary.{Perimeter, Point}

class Abstract3DMapGenerator(val map: Map) extends Perimeter {
  private val buff = collection.mutable.ListBuffer.empty[PieceOfMap]

  def generateMap(): List[PieceOfMap] = {
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
            buff += PieceOfPath(tuple._1, 0, tuple._2)

        case ground: AllGround =>
          for (tuple <- getTypicalTuples(ground.pointsList))
            buff += PieceOfGround(tuple._1, 0, tuple._2)

        case grass: AllGrass =>
          for (tuple <- getTypicalTuples(grass.pointsList))
            buff += PieceOfGrass(tuple._1, 0, tuple._2)

        case _ =>
      }
  }

  private def generateLayer1(): Unit = {
    for (el <- map.getElements)
      el match {
        case tree: Tree =>
          val x_min = tree.pointsList.map(_.x).min
          // korona drzewa
          val y_min = tree.pointsList.map(_.y).min - 1
          val x_max = tree.pointsList.map(_.x).max
          val y_max = tree.pointsList.map(_.y).max

          for (point <- tree.pointsList) {
            val treeMatrix = Array.fill(x_max - x_min + 1, y_max - y_min + 1)(false)
            treeMatrix(point.x - x_min)(point.y - y_min) = true
            buff += PieceOfTree(point, 1, treeMatrix)
          }

        case signpost: Signpost =>
          buff += PieceOfSign(signpost.pointsList.head, 1, Array.fill(1, 1)(true))

        case building: Building =>
          val points = collection.mutable.ListBuffer.empty[Point]

          for (x <- building.leftTopCorner.x to building.rightBottomCorner.x)
            for (y <- building.rightBottomCorner.y - building.heightOfStorey * building.numberOfStoreys + 1
              to building.rightBottomCorner.y)
              points += Point(x, y)

          for (tuple <- getTypicalTuples(points.toList))
            buff += PieceOfWall(tuple._1, 1, tuple._2)

        case _ =>
      }
  }

  private def generateLayer2(): Unit = {
    for (el <- map.getElements)
      el match {
        case tree: Tree =>
          val x_min = tree.pointsList.map(_.x).min
          //korona drzewa
          val y_min = tree.pointsList.map(_.y).min - 1
          val x_max = tree.pointsList.map(_.x).max
          val y_max = tree.pointsList.map(_.y).max

          for (point <- tree.pointsList) {
            val treeMatrix = Array.fill(x_max - x_min + 1, y_max - y_min + 1)(false)
            treeMatrix(point.x - x_min)(point.y - y_min - 1) = true
            buff += PieceOfTree(point - Point(0, 1), 2, treeMatrix)
          }

        case building: Building =>
          for (tuple <- getTypicalTuples(building.pointsList))
            buff += PieceOfRoof(tuple._1 - Point(0, building.heightOfStorey * building.numberOfStoreys), 2, tuple._2)

          // door
          if (building.rightBottomCorner.y + 1 == building.entryPoint.get.y) {
            val doorBottomMatrix = Array.fill(1, 2)(false)
            doorBottomMatrix(0)(1) = true
            val doorTopMatrix = Array.fill(1, 2)(false)
            doorTopMatrix(0)(0) = true
            buff += PieceOfDoor(building.entryPoint.get - Point(0, 1), 2, doorBottomMatrix)
            buff += PieceOfDoor(building.entryPoint.get - Point(0, 2), 2, doorTopMatrix)
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

      for (p <- getPerimeter(List(point)))
        if (points.contains(p))
          matrix(1 - (point - p).x)(1 - (point - p).y) = true
      buff += Tuple2(point, matrix)
    }
    buff.toList
  }
}
