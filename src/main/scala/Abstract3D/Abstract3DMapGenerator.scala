package Abstract3D

import Abstract2D.Map
import Abstract2D.Containers.StreetNetwork
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
        case streetNetwork: StreetNetwork =>
          for (point <- streetNetwork.pointsList) {
            val pathMatrix = Array.fill(3, 3)(false)
            pathMatrix(1)(1) = true
            for (p <- getPerimeter(List(point)))
              if (streetNetwork.pointsList.contains(p))
                pathMatrix(1 - (point - p).x)(1 - (point - p).y) = true
            buff += PieceOfPath(point, 0, pathMatrix)
          }
        case ground: Ground =>
        case grass: Grass =>
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
        case _ =>

        //        case Door =>
        //          map(i)(j)(layer) = Layer1_Wall
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
        case _ =>

        //        case Door =>
        //          map(i)(j)(layer) = Layer2_Door
        //          map(i)(j + 1)(layer) = Layer2_Door
      }
  }

  private def generateLayer3(): Unit = {
    for (el <- map.getElements) {}
  }
}
