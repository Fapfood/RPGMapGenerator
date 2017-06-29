package Abstract2D.Ancillary

import Ancillary.Point
import org.scalatest.FunSuite

class FlatObjectTest extends FunSuite {
  test("Area matches 1") {
    val p1 = Point(5, 5)
    val a: List[Point] = new FlatObject {}.buildBase(List(p1), 20)
    assert(a.size == 20)
  }

  test("Area matches for single point"){
    val p1 = Point(5, 5)
    val a: List[Point] = new FlatObject {}.buildBase(List(p1), 1)
    assert(a.size == 1)
  }

  test("Area matches for zero points"){
    val a: List[Point] = new FlatObject {}.buildBase(List(), 0)
    assert(a.size == 0)
  }

  test("Points are connected 1") {
    val p1 = Point(5, 5)
    val outList: List[Point] = new FlatObject {}.buildBase(List(p1), 30)
    val outSet = outList.toSet
    outList.foreach { p =>
      assert(!outList.filter( pp => pp.distance(p) <= 1.1).isEmpty)
    }
  }
}
