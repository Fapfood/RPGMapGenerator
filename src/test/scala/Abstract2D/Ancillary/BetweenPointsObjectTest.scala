package Abstract2D.Ancillary

import Ancillary.Point
import org.scalatest.FunSuite

/**
  * Created by Samuel on 2017-06-29.
  */
class BetweenPointsObjectTest extends FunSuite {
  test("Start and finish are correct"){
    val p1 = Point(0,0)
    val p2 = Point(10,10)
    val a : List[Point] = new BetweenPointsObject {}.planIdealPath(p1, p2)
    assert(a.head == p1)
    assert(a.last == p2)
  }

  test("Points are connected"){
    val p1 = Point(3,4)
    val p2 = Point(12,14)
    val a : List[Point] = new BetweenPointsObject {}.planIdealPath(p1,p2)
    a.sliding(2).foreach{twoPoints =>
      val (pp1, pp2) = (twoPoints.head, twoPoints.last)
      assert(math.abs(pp1.x - pp2.x) <= 1)
      assert(math.abs(pp1.y - pp2.y) <= 1)
    }
  }
}
