package Base.PathGraph

import Ancillary.Point

class Edge(val startPoint: Point, val endPoint: Point, val weight: Double) {
  override def toString: String = "{" + startPoint.toString + "->" + endPoint.toString + ": " + weight.toString + "}"
}

