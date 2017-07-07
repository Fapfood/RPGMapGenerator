package Base

import Ancillary.Point

class Base(val width: Int, val height: Int) {

  private val array: Array[Array[BaseType.Type]] = Array.fill(height, width)(BaseType.Grass)
  //Array.ofDim[BaseType.Type](height, width)

  override def hashCode(): Int = array.deep.hashCode()

  override def equals(other: Any): Boolean = other match {
    case that: Base =>
      (that canEqual this) && (this.array.deep == that.array.deep)
    case _ =>
      false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Base]

  def apply(x: Int, y: Int): BaseType.Type = array(y)(x)

  def apply(p: Point): BaseType.Type = array(p.y)(p.x)

  def getElements: List[(Point, BaseType.Type)] = {
    val buff = collection.mutable.ListBuffer.empty[(Point, BaseType.Type)]
    for (x <- indices_x)
      for (y <- indices_y)
        buff += Tuple2(Point(x, y), apply(x, y))

    buff.toList
  }

  def add(x: Int, y: Int, value: BaseType.Type): Unit = array(y)(x) = value

  def add(p: Point, value: BaseType.Type): Unit = array(p.y)(p.x) = value

  def indices_x: Range = array(0).indices

  def indices_y: Range = array.indices

  def length: Int = array.flatten.length
}
