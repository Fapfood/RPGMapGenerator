package Abstract2D.Objects

import Abstract2D.Ancillary.{BetweenPointsObject, MapObject}
import Ancillary.{GroupOfArea, Perimeter, Point}

class Path(startPoint: Point, endPoint: Point, workArea: List[Point], obstacles: List[Point]) extends MapObject {
  override val pointsList: List[Point] = planPath(startPoint, endPoint)
  override val hardness: Int = 0

  private def planPath(startPoint: Point, endPoint: Point): List[Point] = {
    if (!workArea.contains(startPoint) || !workArea.contains(endPoint)
      || obstacles.contains(startPoint) || obstacles.contains(endPoint))
      throw new Exception("Wrong end points for Path")


    if (startPoint == endPoint)
      return List(startPoint)

    val graph = createGraphBetween(startPoint, endPoint, obstacles)
    val path = shortestPathsOfDijkstra(graph, startPoint, endPoint)

    val result = collection.mutable.ListBuffer.empty[Point]
    for (i <- 0 to path.length - 2)
      result ++= new BetweenPointsObject {}.planIdealPath(path(i), path(i + 1))
    result.toList
  }

  private class Edge(val startPoint: Point, val endPoint: Point, val weight: Double) {
    override def toString: String = "{" + startPoint.toString + "->" + endPoint.toString + ": " + weight.toString + "}"
  }

  private class Graph(val vertices: Set[Point], val edges: List[Edge]) {
    override def toString: String = vertices.toString + "\n" + edges.toString

    def getWeightOf(startPoint: Point, endPoint: Point): Double = {
      for (e <- edges)
        if (e.startPoint == startPoint && e.endPoint == endPoint)
          return e.weight
      Double.PositiveInfinity
    }

    def getNeighborsOf(point: Point): Set[Point] = {
      val neighbors = collection.mutable.ListBuffer.empty[Point]
      for (e <- edges) {
        if (e.startPoint == point)
          neighbors += e.endPoint
        if (e.endPoint == point)
          neighbors += e.startPoint
      }
      neighbors.toSet
    }
  }

  private def createGraphBetween(startPoint: Point, endPoint: Point, obstacles: List[Point]): Graph = {
    def createVertices(startPoint: Point, endPoint: Point): Set[Point] = {

      val vertices = collection.mutable.ListBuffer.empty[Point]
      for (group <- GroupOfArea.getGroupsOfConnectedPoints(obstacles)) {
        vertices ++= Perimeter.getSignificantPointsOfPerimeter(Perimeter.getPerimeter(group.toList))
      }

      vertices += startPoint
      vertices += endPoint
      vertices.filter(workArea.contains(_)).toSet
    }

    def createEdges(vertices: Set[Point]): List[Edge] = {
      val buff = collection.mutable.ListBuffer.empty[Edge]
      for (v1 <- vertices)
        for (v2 <- vertices)
          if (v1 != v2 && !isSomethingOnTheRoad(v1, v2, obstacles))
            buff += new Edge(v1, v2, v1.distance(v2))
      buff.toList
    }

    def isSomethingOnTheRoad(startPoint: Point, endPoint: Point, obstacles: List[Point]): Boolean = {
      val idealPath = new BetweenPointsObject {}.planIdealPath(startPoint, endPoint).toSet
      (idealPath intersect obstacles.toSet).nonEmpty
    }

    val vertices = createVertices(startPoint, endPoint)
    val edges = createEdges(vertices)
    new Graph(vertices, edges)
  }

  private def shortestPathsOfDijkstra(graph: Graph, startPoint: Point, endPoint: Point): List[Point] = {
    val distance = collection.concurrent.TrieMap.empty[Point, Double]
    val predecessor = collection.concurrent.TrieMap.empty[Point, Point]
    val queue = collection.concurrent.TrieMap.empty[Point, Boolean]
    for (v <- graph.vertices) {
      distance += (v -> Double.PositiveInfinity)
      queue += (v -> true)
    }

    def queueIsNotEmpty(): Boolean = {
      for (p <- queue.values)
        if (p) return true
      false
    }

    distance.replace(startPoint, 0)

    while (queueIsNotEmpty()) {
      var min = Double.PositiveInfinity
      var u: Point = Point()
      for (v <- distance.keys)
        if (distance(v) < min && queue(v)) {
          min = distance(v)
          u = v
        }
      queue.replace(u, false)

      for (v <- graph.getNeighborsOf(u))
        if (distance(v) > distance(u) + graph.getWeightOf(u, v)) {
          distance.replace(v, distance(u) + graph.getWeightOf(u, v))
          predecessor += (v -> u)
          queue.replace(v, true)
        }
    }

    val result = collection.mutable.ListBuffer.empty[Point]
    var p = endPoint
    result += p
    while (p != startPoint) {
      p = predecessor(p)
      result += p
    }
    result.toList.reverse
  }
}