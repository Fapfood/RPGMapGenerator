package Abstract2D.Ancillary

import Ancillary.{Perimeter, Point}

import scala.util.Random

trait FlatObject {
  def buildBase(previousPoints: List[Point], area: Int, weights: List[Int] = List(1, 1)): List[Point] = {
    if (previousPoints.length > area)
      throw new Exception("Too little area in FlatObject.build")

    val impElemsToWeight = collection.mutable.Map.empty[Int, Int]
    for (i <- weights.indices)
      impElemsToWeight.put(i, weights(i))

    var shape = previousPoints
    for (_ <- previousPoints.length + 1 to area) {
      val nearestPoints = this.nearestPoints(shape, impElemsToWeight.toMap)
      val previousPoint = nearestPoints(Random.nextInt(nearestPoints.length))
      shape = previousPoint :: shape
    }
    shape
  }

  private def nearestPoints(previousPoints: List[Point], impElemsToWeight: collection.immutable.Map[Int, Int]): List[Point] = {
    val importantElems = impElemsToWeight.keys.size - 1
    if (impElemsToWeight.keys.min != 0 || impElemsToWeight.keys.max != importantElems)
      throw new Exception("Wrong weight map in nearest points method")

    val pointToDistance = collection.mutable.Map.empty[Point, Int]
    for (consideredPoint <- new Perimeter {}.getPerimeter(previousPoints))
      if (!pointToDistance.contains(consideredPoint)) {
        var distance = consideredPoint.stepDistance(previousPoints.last) * impElemsToWeight(0) // to start point
        var tailed = previousPoints
        var i = 1
        while (i <= importantElems && tailed.length > 1) {
          distance += consideredPoint.stepDistance(tailed.head) * impElemsToWeight(i) // to last added points
          tailed = tailed.tail
          i += 1
        }
        pointToDistance.put(consideredPoint, distance)
      }
    val minDistance = pointToDistance.values.min
    val nearestPoints = pointToDistance.retain((_, v) => v == minDistance).keys.toList
    nearestPoints
  }
}
