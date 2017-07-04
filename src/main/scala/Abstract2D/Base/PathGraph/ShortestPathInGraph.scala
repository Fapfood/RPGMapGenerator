package Abstract2D.Base.PathGraph

import Ancillary.Point

object ShortestPathInGraph {

  def shortestPathsOfDijkstra(graph: Graph, startPoint: Point, endPoint: Point): List[Point] = {
    val distance = collection.concurrent.TrieMap.empty[Point, Double]
    val predecessor = collection.concurrent.TrieMap.empty[Point, Point]
    val queue = collection.concurrent.TrieMap.empty[Point, Boolean]
    for (v <- graph.getVertices) {
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

  def shortestPathsOfFloydWarshall(graph: Graph): (Map[(Point, Point), Double], Map[(Point, Point), Point]) = {
    val distance = collection.concurrent.TrieMap.empty[(Point, Point), Double]
    val successor = collection.concurrent.TrieMap.empty[(Point, Point), Point]

    for (v1 <- graph.getVertices) {
      for (v2 <- graph.getVertices)
        distance += (Tuple2(v1, v2) -> Double.PositiveInfinity)
      distance.replace(Tuple2(v1, v1), 0)
    }

    for (e <- graph.getEdges) {
      distance.replace(Tuple2(e.startPoint, e.endPoint), e.weight)
      successor += (Tuple2(e.startPoint, e.endPoint) -> e.endPoint)
    }

    for (u <- graph.getVertices)
      for (v1 <- graph.getVertices)
        for (v2 <- graph.getVertices)
          if (distance(Tuple2(v1, v2)) > distance(Tuple2(v1, u)) + distance(Tuple2(u, v2))) {
            distance.replace(Tuple2(v1, v2), distance(Tuple2(v1, u)) + distance(Tuple2(u, v2)))
            successor += (Tuple2(v1, v2) -> successor(Tuple2(v1, u)))
          }

    Tuple2(distance.toMap, successor.toMap)
  }
}
