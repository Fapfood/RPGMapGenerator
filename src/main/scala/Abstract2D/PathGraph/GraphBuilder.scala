package Abstract2D.PathGraph

import Abstract2D.Ancillary.BetweenPointsObject
import Ancillary.{GroupOfArea, Perimeter, Point}

object GraphBuilder {
  def createVerticesAround(obstacles: List[Point], workArea: List[Point]): Set[Point] = {
    val vertices = collection.mutable.ListBuffer.empty[Point]

    for (group <- GroupOfArea.getGroupsOfConnectedPoints(obstacles))
      vertices ++= Perimeter.getSignificantPointsOfPerimeter(Perimeter.getPerimeter(group.toList))

    vertices.filter(workArea.contains(_)).toSet
  }

  def createEdgesBetween(vertices: Set[Point], obstacles: List[Point]): Set[Edge] = {
    val buff = collection.mutable.ListBuffer.empty[Edge]
    for (v1 <- vertices)
      for (v2 <- vertices)
        if (v1 != v2 && !isSomethingOnTheRoad(v1, v2, obstacles))
          buff += new Edge(v1, v2, v1.distance(v2))
    buff.toSet
  }

  private def isSomethingOnTheRoad(startPoint: Point, endPoint: Point, obstacles: List[Point]): Boolean = {
    val idealPath = BetweenPointsObject.planIdealPath(startPoint, endPoint).toSet
    (idealPath intersect obstacles.toSet).nonEmpty
  }
}
