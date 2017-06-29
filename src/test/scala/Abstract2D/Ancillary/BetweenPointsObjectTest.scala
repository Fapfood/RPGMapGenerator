package Abstract2D.Ancillary

import Ancillary.Point
import org.scalatest.FunSuite


class BetweenPointsObjectTest extends FunSuite {
  test("Start and finish are correct") {
    val p1 = Point(0, 0)
    val p2 = Point(10, 10)
    val a: List[Point] = new BetweenPointsObject {}.planIdealPath(p1, p2)
    assert(a.head == p1)
    assert(a.last == p2)
  }

  test("Points are connected") {
    val p1 = Point(3, 4)
    val p2 = Point(12, 14)
    val a: List[Point] = new BetweenPointsObject {}.planIdealPath(p1, p2)
    a.sliding(2).foreach { twoPoints =>
      val (pp1, pp2) = (twoPoints.head, twoPoints.last)
      assert(math.abs(pp1.x - pp2.x) <= 1)
      assert(math.abs(pp1.y - pp2.y) <= 1)
    }
  }

  test("Regular test 1") {
    val p1 = Point(1, 4)
    val p2 = Point(9, 1)
    val a = new BetweenPointsObject {}.planIdealPath(p1, p2)
    val expectedOut = List(
      Point(1, 4),
      Point(2, 4),
      Point(2, 3),
      Point(3, 3),
      Point(4, 3),
      Point(5, 3),
      Point(5, 2),
      Point(6, 2),
      Point(7, 2),
      Point(8, 2),
      Point(8, 1),
      Point(9, 1)
    )
    assert(a == expectedOut)
  }

  test("Regular test 1 flipped") {
    val p1 = Point(4, 1)
    val p2 = Point(1, 9)
    val a = new BetweenPointsObject {}.planIdealPath(p1, p2)
    val expectedOut = List(
      Point(4, 1),
      Point(4, 2),
      Point(3, 2),
      Point(3, 3),
      Point(3, 4),
      Point(3, 5),
      Point(2, 5),
      Point(2, 6),
      Point(2, 7),
      Point(2, 8),
      Point(1, 8),
      Point(1, 9)
    )
    assert(a == expectedOut)
  }

  test("Vertical line test") {
    val p1 = Point(2, 0)
    val p2 = Point(2, 5)
    val a = new BetweenPointsObject {}.planIdealPath(p1, p2)
    val expectedOut = List(
      Point(2, 0),
      Point(2, 1),
      Point(2, 2),
      Point(2, 3),
      Point(2, 4),
      Point(2, 5)
    )
    assert(a == expectedOut)
  }

  test("Single point path") {
    val p1 = Point(1, 1)
    val a = new BetweenPointsObject {}.planIdealPath(p1, p1)
    assert(a == List(Point(1, 1)))
  }

  test("Single point path for different objects") {
    val p1 = Point(1, 1)
    val p2 = Point(1, 1)
    val a = new BetweenPointsObject {}.planIdealPath(p1, p2)
    assert(a == List(Point(1, 1)))
  }

  test("Single point path for (4,4)") {
    val p1 = Point(4, 4)
    val a = new BetweenPointsObject {}.planIdealPath(p1, p1)
    assert(a == List(Point(4, 4)))
  }

  test("Single point path for (4,4) with different objects") {
    val p1 = Point(4, 4)
    val p2 = Point(4, 4)
    val a = new BetweenPointsObject {}.planIdealPath(p1, p2)
    assert(a == List(Point(4, 4)))
  }
}
