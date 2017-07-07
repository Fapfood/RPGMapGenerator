package Generators

import Abstract2D.DTO_Map
import Abstract2D.Objects._
import GUI.DTO_MapParameters
import _root_.Ancillary.Point

import scala.util.Random

class Abstract2DMapGenerator(mapParams: DTO_MapParameters) {
  def generateMap: DTO_Map = {

    val map = new DTO_Map(mapParams.width, mapParams.height)

    for (_ <- 1 to mapParams.numberOfHouses) {
      val building = generateBuilding(map)
      if (building.nonEmpty)
        map.addElement(building.get)
    }

    for (_ <- 1 to mapParams.numberOfTrees) {
      val tree = generateTree(map)
      if (tree.nonEmpty)
        map.addElement(tree.get)
    }

    for (_ <- 1 to mapParams.numberOfExits) {
      val sign = generateSign(map)
      if (sign.nonEmpty)
        map.addElement(sign.get)
    }

    map
  }

  private def generateBuilding(map: DTO_Map): Option[Building] = {
    var tried = 0
    var building: Building = new Building(Point(), Point(), List(), 0)
    do {
      val width = getRandomInt(0.6)
      val height = getRandomInt(0.4)
      val x = Random.nextInt(mapParams.width - width)
      val y = Random.nextInt(mapParams.height - height - 2) + 2
      building = new Building(Point(x, y), Point(x + width - 1, y + height - 1), map.pointsList, 2)
      tried += 1
    } while (!isAreaFree(building.pointsList ++ building.entryPoint, map.getObstacles)
      || !belongAreaToArea(building.pointsList ++ building.entryPoint, map.pointsList) || tried >= 20)
    if (tried < 20) Option(building) else None
  }

  private def generateTree(map: DTO_Map): Option[Tree] = {
    var tried = 0
    var tree: Tree = new Tree(List())
    do {
      val width = Random.nextInt(2) + 1
      val x = Random.nextInt(mapParams.width - width + 1)
      val y = Random.nextInt(mapParams.height - 1) + 1
      val roots = if (width == 1) List(Point(x, y)) else List(Point(x, y), Point(x + 1, y))
      tree = new Tree(roots)
      tried += 1
    } while (!isAreaFree(tree.pointsList, map.getObstacles)
      || !belongAreaToArea(tree.pointsList, map.pointsList) || tried >= 20)
    if (tried < 20) Option(tree) else None
  }

  private def generateSign(map: DTO_Map): Option[Signpost] = {
    var tried = 0
    var sign: Signpost = new Signpost(Point(), Point())
    do {
      val x = Random.nextInt(mapParams.width)
      val y = Random.nextInt(mapParams.height)
      val signPoint = List(Point(x, 0), Point(x, mapParams.height - 1), Point(0, y), Point(mapParams.width - 1, y))
        .apply(Random.nextInt(4))
      val entryPoints = List(signPoint + Point(0, 1), signPoint - Point(0, 1), signPoint + Point(1, 0), signPoint - Point(1, 0))
        .filter(map.pointsList.contains(_))
      val entryPoint = entryPoints(Random.nextInt(entryPoints.length))
      sign = new Signpost(signPoint, entryPoint)
      tried += 1
    } while (!isAreaFree(sign.pointsList ++ sign.entryPoint, map.getObstacles)
      || !belongAreaToArea(sign.pointsList ++ sign.entryPoint, map.pointsList) || tried >= 20)
    if (tried < 20) Option(sign) else None
  }

  private def getRandomInt(factor: Double): Int = {
    var i = 1

    while (Random.nextDouble() <= factor)
      i += 1

    if (i < 3) 3 else if (i > Math.max(mapParams.width, mapParams.height) - 5) 8 else i
  }

  private def isAreaFree(area: List[Point], obstacles: List[Point]): Boolean =
    (area.toSet intersect obstacles.toSet).isEmpty

  private def belongAreaToArea(smallArea: List[Point], bigArea: List[Point]): Boolean =
    (smallArea.toSet intersect bigArea.toSet) == smallArea.toSet
}