package Base.PathFindAndUnion

import Abstract2D.Ancillary.BetweenPointsObject
import Abstract2D.Base.PathGraph.{Graph, ShortestPathInGraph}
import Ancillary.Point
import Base.PathGraph.{Graph, ShortestPathInGraph}

class MinimumSpanningTree {

  private class Edge(val startPoint: Node, val endPoint: Node, val weight: Double)

  def MST_Kruskal(entrypoints: List[Point], graph: Graph): List[Point] = {
    val distancesAndSuccessors = ShortestPathInGraph.shortestPathsOfFloydWarshall(graph)
    val distances = distancesAndSuccessors._1
    val successors = distancesAndSuccessors._2

    val fau = new FindAndUnion

    val makeSeted = collection.mutable.ListBuffer.empty[Node]
    for (v <- entrypoints)
      makeSeted += fau.makeSet(v)
    val vertices = makeSeted.toSet

    val tmpEdges = collection.mutable.ListBuffer.empty[Edge]
    for (v1 <- vertices)
      for (v2 <- vertices)
        if (v1 != v2)
          tmpEdges += new Edge(v1, v2, distances(Tuple2(v1.value, v2.value)))
    val edges = tmpEdges.toList.distinct.sortBy(_.weight)

    val mst = collection.mutable.ListBuffer.empty[Edge]
    for (e <- edges)
      if (fau.findSet(e.startPoint) != fau.findSet(e.endPoint)) {
        mst += e
        fau.union(e.startPoint, e.endPoint)
      }

    val result = collection.mutable.ListBuffer.empty[Point]
    for (e <- mst) {
      val pathPoints = pathReconstruction(e.startPoint.value, e.endPoint.value, successors)
      for (i <- 0 to pathPoints.length - 2)
        result ++= BetweenPointsObject.planIdealPath(pathPoints(i), pathPoints(i + 1))
    }

    result.toList.distinct
  }

  private def pathReconstruction(startPoint: Point, endPoint: Point,
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

}
