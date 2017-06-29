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
}
