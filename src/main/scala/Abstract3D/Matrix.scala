package Abstract3D

class Matrix private(private val array: Array[Array[Boolean]]) {
  override def hashCode(): Int = array.deep.hashCode()

  override def equals(other: Any): Boolean = other match {
    case that: Matrix =>
      (that canEqual this) && (this.array.deep == that.array.deep)
    case _ =>
      false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Matrix]

  def apply(x: Int, y: Int): Boolean = array(y)(x)

  def indices_x: Range = array(0).indices

  def indices_y: Range = array.indices

  def length: Int = array.flatten.length
}

object Matrix {
  def apply(array: Array[Array[Boolean]]) = new Matrix(array)

  def apply(one: Boolean) = new Matrix(Array.fill(1, 1)(one))
}
