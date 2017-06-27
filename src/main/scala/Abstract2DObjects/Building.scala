package Abstract2DObjects

import Abstract2DAncillary.{BlockObject, Perimeter, Point, RectangularObject}

import scala.util.Random

class Building(leftTopCorner: Point, rightDownCorner: Point, door: Point, wallHeight: Int)
  extends MapObject with BlockObject {
  override val pointsList: List[Point] = new RectangularObject {}.buildBase(leftTopCorner, rightDownCorner)
  override protected val heightOfStorey: Int = wallHeight
  override protected val numberOfStoreys: Int = 1
  override val hardness: Int = 1
  override val entryPoint: Option[Point] = Option(chooseDoor())

  private def chooseDoor(): Point = {
    val points = new Perimeter {}.getPerimeter(pointsList)
      .filter(p => if (p.x < leftTopCorner.x + 1
        || p.x > rightDownCorner.x - 1
        || p.y < leftTopCorner.y + 1
        || p.y > rightDownCorner.y - 1) false else true)
    points(Random.nextInt(points.length))
  }

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
}
