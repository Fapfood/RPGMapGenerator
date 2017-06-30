package Abstract2D.Base

import Abstract2D.Ancillary.MapObject
import Ancillary.Point

class Path(startPoint: Point, endPoint: Point, workArea: List[Point], obstacles: List[Point]) extends MapObject {
  override val pointsList: List[Point] = planPath(startPoint, endPoint)
  override val hardness: Int = 0

  private def planPath(startPoint: Point, endPoint: Point): List[Point] = {
    if (!workArea.contains(startPoint) || !workArea.contains(endPoint)
      || obstacles.contains(startPoint) || obstacles.contains(endPoint))
      throw new Exception("Wrong end points for Path: %s, %s".format(startPoint, endPoint))


    if (startPoint == endPoint)
      return List(startPoint)

//    val graph = GraphBuilder.createGraphBetween(startPoint, endPoint, obstacles, workArea)
//    val path = ShortestPathInGraph.shortestPathsOfDijkstra(graph, startPoint, endPoint)

    val result = collection.mutable.ListBuffer.empty[Point]
//    for (i <- 0 to path.length - 2)
//      result ++= new BetweenPointsObject {}.planIdealPath(path(i), path(i + 1))
//    println(result.toList)
    result.toList
  }
}