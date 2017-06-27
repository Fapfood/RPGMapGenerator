package Abstract2D.Containers

import Abstract2D.Map
import Abstract2D.Ancillary.MapObject
import Abstract2D.Objects.Path
import Ancillary.Point

class AllPaths(map: Map) extends MapObject {
  private val buff = collection.mutable.ListBuffer.empty[Point]
  override val pointsList: List[Point] = concatenatePaths()
  override val hardness: Int = 0

  private def concatenatePaths(): List[Point] = {
    val firstClassPoints = collection.mutable.ListBuffer.empty[Point]
    val secondClassPoints = collection.mutable.ListBuffer.empty[Point]
    for (p <- getEntryPoints)
      if (isOutsideMap(p))
        firstClassPoints += p
      else
        secondClassPoints += p

    var midpoint = findMidpoint(firstClassPoints.toList)
    if (firstClassPoints.length < 2)
      midpoint = findMidpoint(secondClassPoints.toList)

    if (firstClassPoints.nonEmpty) {
      buff ++= new Path(firstClassPoints.head, midpoint, map).pointsList
      for (p <- firstClassPoints.tail)
        buff ++= new Path(p, getClosestPointToPath(p), map).pointsList
      for (p <- secondClassPoints)
        buff ++= new Path(p, getClosestPointToPath(p), map).pointsList
    } else {
      buff ++= new Path(secondClassPoints.head, midpoint, map).pointsList
      for (p <- secondClassPoints.tail)
        buff ++= new Path(p, getClosestPointToPath(p), map).pointsList
    }

    buff.toSet.toList
  }

  private def getEntryPoints: List[Point] = {
    val buff = collection.mutable.ListBuffer.empty[Point]

    for (e <- map.getElements)
      if (e.entryPoint.isDefined)
        buff += e.entryPoint.get

    buff.toList
  }

  private def isOutsideMap(point: Point): Boolean = {
    if (point.x < 0 || point.x > map.x - 1 || point.y < 0 || point.y > map.y - 1)
      true
    else
      false
  }

  private def findMidpoint(points: List[Point]): Point = {
    var distance = Double.PositiveInfinity
    var finded = Point(-1, -1)

    for (x <- 0 until map.x)
      for (y <- 0 until map.y) {
        val point = Point(x, y)
        var newDistance = 0.0
        for (p <- points)
          newDistance += point.distance(p)
        if (distance > newDistance && !map.isHardOccupied(point)) {
          distance = newDistance
          finded = point
        }
      }

    finded
  }

  private def getClosestPointToPath(point: Point): Point = {
    var distance = Double.PositiveInfinity
    var finded = Point(-1, -1)

    for (p <- buff.toList)
      if (point.distance(p) < distance) {
        distance = point.distance(p)
        finded = point
      }

    finded
  }
}
