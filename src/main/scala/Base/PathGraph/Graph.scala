package Base.PathGraph

import Ancillary.Point

class Graph(private var vertices: Set[Point], private var edges: Set[Edge]) {
  override def toString: String = vertices.toString + "\n" + edges.toString

  def getVertices: Set[Point] = vertices

  def getEdges: Set[Edge] = edges

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

  def addVerticesAndEdges(vertices: Set[Point], edges: Set[Edge]): Unit = {
    this.vertices = this.vertices union vertices
    this.edges = this.edges union edges
  }
}