package Abstract3D

import Abstract2DAncillary.{Perimeter, Point}
import Abstract2DObjects.{Building, Map, Signpost, StreetNetwork, Tree}

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
          for (point <- streetNetwork.getFields) {
            val pathMatrix = Array.fill(3, 3)(false)
            pathMatrix(1)(1) = true
            for (p <- getPerimeter(List(point)))
              if (streetNetwork.getFields.contains(p))
                pathMatrix(1 - (point - p).x)(1 - (point - p).y) = true
            buff += PieceOfPath(point, 0, pathMatrix)
          }
        case _ =>

        //        case Ground =>
        //          map(i)(j)(layer) = Layer0_Grass
      }
  }

  private def generateLayer1(): Unit = {
    for (el <- map.getElements)
      el match {
        case tree: Tree =>
          val x_min = tree.getFields.map(_.x).min
          // korona drzewa
          val y_min = tree.getFields.map(_.y).min - 1
          val x_max = tree.getFields.map(_.x).max
          val y_max = tree.getFields.map(_.y).max

          for (point <- tree.getFields) {
            val treeMatrix = Array.fill(x_max - x_min + 1, y_max - y_min + 1)(false)
            treeMatrix(point.x - x_min)(point.y - y_min) = true
            buff += PieceOfTree(point, 1, treeMatrix)
          }

        case signpost: Signpost =>
          buff += PieceOfSign(signpost.getFields.head, 1, Array.fill(1, 1)(true))

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
          val x_min = tree.getFields.map(_.x).min
          //korona drzewa
          val y_min = tree.getFields.map(_.y).min - 1
          val x_max = tree.getFields.map(_.x).max
          val y_max = tree.getFields.map(_.y).max

          for (point <- tree.getFields) {
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
