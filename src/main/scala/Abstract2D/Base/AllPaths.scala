package Abstract2D.Base

import Abstract2D.Ancillary.BetweenPointsObject
import Abstract2D.Base.PathGraph.{Graph, GraphBuilder, ShortestPathInGraph}
import Abstract2D.{Map, MapObject}
import Ancillary.Point

class AllPaths(map: Map) extends MapObject {
  private val buff = collection.mutable.ListBuffer.empty[Point]
  override val pointsList: List[Point] = concatenatePaths()
  override val hardness: Int = 0

  private def concatenatePaths(): List[Point] = {
    if (getEntryPoints.nonEmpty) {
      val obstacles = map.getObstacles
      val workArea = map.pointsList
      val vertices = GraphBuilder.createVerticesAround(obstacles, workArea) ++ getEntryPoints
      val edges = GraphBuilder.createEdgesBetween(vertices, obstacles)
      val graph = new Graph(vertices, edges)

      val distancesAndSuccessors = ShortestPathInGraph.shortestPathsOfFloydWarshall(graph)
      val distances = distancesAndSuccessors._1
      val successors = distancesAndSuccessors._2

      for (entryPoint1 <- getEntryPoints)
        for (entryPoint2 <- getEntryPoints) {
          if (entryPoint1 != entryPoint2 && !areConnected(entryPoint1, entryPoint2)) {

            val pathPoints = pathReconstruction(entryPoint1, entryPoint2, distances, successors)
            val result = collection.mutable.ListBuffer.empty[Point]
            for (i <- 0 to pathPoints.length - 2)
              buff ++= BetweenPointsObject.planIdealPath(pathPoints(i), pathPoints(i + 1))
            //            buff ++= result
            //            graph.addVerticesAndEdges(result.toSet, GraphBuilder.createEdgesBetween(vertices ++ result, obstacles))
          }
        }
    }

    buff.toSet.toList
  }

  private def pathReconstruction(startPoint: Point, endPoint: Point, distances: collection.Map[(Point, Point), Double],
                                 successors: collection.Map[(Point, Point), Point]): List[Point] = {

    if (!successors.contains(Tuple2(startPoint, endPoint)))
      return List()

    val result = collection.mutable.ListBuffer.empty[Point]
    var p = startPoint
    result += p
    while (p != endPoint) {
      p = successors(Tuple2(p, endPoint))
      result += p
    }
    result.toList
  }

  private def areConnected(p1: Point, p2: Point): Boolean =
    buff.contains(p1) && buff.contains(p2)

  private def getEntryPoints: List[Point] = {
    val buff = collection.mutable.ListBuffer.empty[Point]

    for (e <- map.getElements)
      if (e.entryPoint.isDefined)
        buff += e.entryPoint.get

    buff.toList
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
        finded = p
      }

    finded
  }
}
