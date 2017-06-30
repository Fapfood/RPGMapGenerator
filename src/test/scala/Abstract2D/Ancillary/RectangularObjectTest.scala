package Abstract2D.Ancillary

import Ancillary.Point
import org.scalatest.FunSuite

class RectangularObjectTest extends FunSuite {
  test("Area check 1") {
    val p1 = Point(1, 1)
    val p2 = Point(5, 5)
    val out = RectangularObject.buildBase(p1, p2)
    assert(out.size == 25)
  }

  test("Area check 2") {
    val p1 = Point(2, 2)
    val p2 = Point(4, 4)
    val out = RectangularObject.buildBase(p1, p2)
    assert(out.size == 9)
  }

  test("Output check") {
    val p1 = Point(1, 2)
    val p2 = Point(2, 5)
    val out = RectangularObject.buildBase(p1, p2)
    val expectedSet = Set(
      Point(1, 2),
      Point(2, 2),
      Point(1, 3),
      Point(2, 3),
      Point(1, 4),
      Point(2, 4),
      Point(1, 5),
      Point(2, 5)
    )
    assert(out.toSet == expectedSet)
  }
}
