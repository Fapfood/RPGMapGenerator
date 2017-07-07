package Base

import Abstract2D.{Map, MapObject}
import Ancillary.Point
import Base.PathFindAndUnion.MinimumSpanningTree
import Base.PathGraph.{Graph, GraphBuilder}

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

      buff ++= new MinimumSpanningTree().MST_Kruskal(getEntryPoints, graph)
    }

    buff.toList.distinct
  }

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
}
