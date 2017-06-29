package Abstract2D.Objects

import Abstract2D.Ancillary.{BlockObject, MapObject, RectangularObject}
import Ancillary.{Perimeter, Point}

import scala.util.Random

class Building(val topLeft: Point, val bottomRight: Point, workArea: List[Point], wallHeight: Int)
  extends MapObject with BlockObject {
  override val pointsList: List[Point] = new RectangularObject {}.buildBase(topLeft, bottomRight)
  override val heightOfStorey: Int = wallHeight
  override val numberOfStoreys: Int = 1
  override val hardness: Int = 1
  override val entryPoint: Option[Point] = Option(chooseDoor())

  private def chooseDoor(): Point = {
    val points = Perimeter.getPerimeter(pointsList)
      .filter(p => if (
        ((p.x >= topLeft.x + 1
          && p.x <= bottomRight.x - 1)
          || (p.y >= topLeft.y + 1
          && p.y <= bottomRight.y - 1))
          && workArea.contains(p)
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
