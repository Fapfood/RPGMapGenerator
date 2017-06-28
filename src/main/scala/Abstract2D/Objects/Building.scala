package Abstract2D.Objects

import Abstract2D.Ancillary.{BlockObject, MapObject, RectangularObject}
import Ancillary.{Perimeter, Point}

import scala.util.Random

class Building(val leftTopCorner: Point, val rightBottomCorner: Point, wallHeight: Int)
  extends MapObject with BlockObject {
  override val pointsList: List[Point] = new RectangularObject {}.buildBase(leftTopCorner, rightBottomCorner)
  override val heightOfStorey: Int = wallHeight
  override val numberOfStoreys: Int = 1
  override val hardness: Int = 1
  override val entryPoint: Option[Point] = Option(chooseDoor())

  private def chooseDoor(): Point = {
    val points = new Perimeter {}.getPerimeter(pointsList)
      .filter(p => if (
        (p.x >= leftTopCorner.x + 1
          && p.x <= rightBottomCorner.x - 1)
          || (p.y >= leftTopCorner.y + 1
          && p.y <= rightBottomCorner.y - 1)
      ) true else false)
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
