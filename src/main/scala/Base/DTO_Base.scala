package Base

import Ancillary.{Point, RectangularObject}

class DTO_Base(val width: Int, val height: Int) {

  private val array: Array[Array[BaseType.Type]] =
    Array.ofDim[BaseType.Type](height, width)

  override def hashCode(): Int = array.deep.hashCode()

  override def equals(other: Any): Boolean = other match {
    case that: DTO_Base =>
      (that canEqual this) && (this.array.deep == that.array.deep)
    case _ =>
      false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[DTO_Base]

  def apply(x: Int, y: Int): BaseType.Type = array(y)(x)

  def apply(p: Point): BaseType.Type = array(p.y)(p.x)

  def getElements: List[(Point, BaseType.Type)] = {
    val buff = collection.mutable.ListBuffer.empty[(Point, BaseType.Type)]
    val points = RectangularObject.buildBase(Point(), Point(width - 1, height - 1))
    for (point <- points)
      buff += Tuple2(point, apply(point))

    buff.toList
  }

  def +=(x: Int, y: Int, value: BaseType.Type): Unit = array(y)(x) = value

  def +=(p: Point, value: BaseType.Type): Unit = array(p.y)(p.x) = value

  def ++=(points: List[Point], value: BaseType.Type): Unit =
    for (p <- points)
      this += (p, value)

  def indices_x: Range = array(0).indices

  def indices_y: Range = array.indices

  def length: Int = array.flatten.length
}