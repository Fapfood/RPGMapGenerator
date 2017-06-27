package Abstract2DObjects

import Abstract2DAncillary.{BlockObject, Point}

import scala.util.Random

class Building(leftTopCorner: Point, rightDownCorner: Point, door: Point, wallHeight: Int)
  extends MapObject with BlockObject {
  override val pointsList: List[Point] = generateFloor()
  override val heightOfStorey: Int = wallHeight
  override val numberOfStoreys: Int = 1
  override val hardness: Int = 1

  //  object CasualShape extends Enumeration {
  //    type Shape = Value
  //    val T, +, |, L = Value
  //
  //    def randomShape(): Shape = CasualShape(Random.nextInt(CasualShape.maxId))
  //  }
  //
  //  object EpicShape extends Enumeration {
  //    type Shape = Value
  //    val U, H, E, O = Value
  //
  //    def randomShape(): Shape = EpicShape(Random.nextInt(EpicShape.maxId))
  //  }

  private def generateFloor(): List[Point] = {
    val buff = collection.mutable.ListBuffer.empty[Point]
    for (i <- leftTopCorner.x to rightDownCorner.x)
      for (j <- leftTopCorner.y to rightDownCorner.y)
        buff += Point(i, j)
    buff.toList
  }
}
