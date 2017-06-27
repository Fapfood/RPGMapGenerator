package Abstract2D.Objects

import Abstract2D.Map
import Abstract2D.Ancillary.{BetweenPointsObject, MapObject}
import Ancillary.{Perimeter, Point}

class Path(startPoint: Point, endPoint: Point, map: Map) extends MapObject {
  override val pointsList: List[Point] = planPath(startPoint, endPoint, map)
  override val hardness: Int = 0

  private def planPath(startPoint: Point, endPoint: Point, map: Map): List[Point] = {
    val graph = createGraphBetween(startPoint, endPoint, map)
    val path = shortestPathsOfDijkstra(graph, startPoint, endPoint)

    val result = collection.mutable.ListBuffer.empty[Point]
    for (i <- 0 to path.length - 2)
      result ++= new BetweenPointsObject {}.planIdealPath(path(i), path(i + 1))
    result.toList
  }

  private def isSomethingOnTheRoad(startPoint: Point, endPoint: Point, map: Map): Option[Point] = {
    val idealPath = new BetweenPointsObject {}.planIdealPath(startPoint, endPoint)
    var currentPoint = idealPath.head
    var tailedPath = idealPath.tail

    while (tailedPath.nonEmpty) {
      if (map.isHardOccupied(currentPoint))
        return Some(currentPoint)
      currentPoint = tailedPath.head
      tailedPath = tailedPath.tail
    }
    None
  }

  private def createVertices(startPoint: Point, endPoint: Point, map: Map): Set[Point] = {
    if (map.isHardOccupied(startPoint) || map.isHardOccupied(endPoint))
      throw new Exception("Wrong end points in Path.createVertices")

    val vertices = collection.mutable.ListBuffer.empty[Point]
    vertices += endPoint

    def subVertices(startPoint: Point, endPoint: Point): Unit = {
      if (vertices.contains(startPoint))
        return

      vertices += startPoint

      isSomethingOnTheRoad(startPoint, endPoint, map) match {
        case None =>
        case Some(point) =>
          val obstacle = map.getGroupOfHardPointsConnectedWith(point)
          val putativePoints = new Perimeter {}.getSignificantPointsOfPerimeter(new Perimeter {}.getPerimeter(obstacle))
          for (p <- putativePoints)
            subVertices(p, endPoint)
      }
    }

    subVertices(startPoint, endPoint)
    vertices.toSet
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

  private def createEdges(vertices: Set[Point], map: Map): List[Edge] = {
    val buff = collection.mutable.ListBuffer.empty[Edge]
    for (v1 <- vertices)
      for (v2 <- vertices)
        if (v1 != v2 && isSomethingOnTheRoad(v1, v2, map).isEmpty)
          buff += new Edge(v1, v2, v1.distance(v2))
    buff.toList
  }

  private def createGraphBetween(startPoint: Point, endPoint: Point, map: Map): Graph = {
    val vertices = createVertices(startPoint, endPoint, map)
    val edges = createEdges(vertices, map)
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