package Abstract2D

import Abstract2D.Containers.{AllGrass, AllGround, AllPaths}
import Abstract2D.Objects._
import _root_.Ancillary.Point
import GUI.MapParameters
import scala.util.Random

class Abstract2DMapGenerator(val mapParams: MapParameters) {
  def generateMap: Map = {

    val map = new Map(mapParams.width, mapParams.height)

    map.addElement(
      new AllGrass(new Grass(map.getTopLeftCorner, map.getBottomRightCorner), new AllGround(List.empty[Ground])))

    for (_ <- 1 to mapParams.numberOfHouses)
      map.addElement(generateBuilding(map))

    for (i <- 1 to mapParams.numberOfTrees)
      map.addElement(generateTree(map))

    //    map.addElement(new Signpost(Point(8, 0), Point(9, 0)))
    //    map.addElement(new Signpost(Point(0, 1), Point(0, 0)))

//    map.addElement(new AllPaths(map))
    map
  }

  private def generateBuilding(map: Map): Building = {
    var building: Building = new Building(Point(), Point(), List(), 0)
    do {
      val width = getRandomInt(0.6)
      val height = getRandomInt(0.5)
      val x = Random.nextInt(mapParams.width - width)
      val y = Random.nextInt(mapParams.height - height - 2) + 2
      building = new Building(Point(x, y), Point(x + width - 1, y + height - 1), map.pointsList, 2)
    } while (!isAreaFree(building.pointsList, map.getObstacles) || !belongAreaToArea(building.pointsList, map.pointsList))
    building
  }

  private def generateTree(map: Map): Tree = {
    var tree: Tree = new Tree(List())
    do {
      val width = Random.nextInt(2) + 1
      val x = Random.nextInt(mapParams.width - width + 1)
      val y = Random.nextInt(mapParams.height - 1) + 1
      val roots = if (width == 1) List(Point(x, y)) else List(Point(x, y), Point(x + 1, y))
      tree = new Tree(roots)
    } while (!isAreaFree(tree.pointsList, map.getObstacles) || !belongAreaToArea(tree.pointsList, map.pointsList))
    tree
  }

  private def getRandomInt(factor: Double): Int = {
    var i = 1

    while (Random.nextDouble() <= factor)
      i += 1

    if (i < 2) 2 else i
  }

  private def isAreaFree(area: List[Point], obstacles: List[Point]): Boolean =
    (area.toSet intersect obstacles.toSet).isEmpty

  private def belongAreaToArea(smallArea: List[Point], bigArea: List[Point]): Boolean =
    (smallArea.toSet intersect bigArea.toSet) == smallArea.toSet
}