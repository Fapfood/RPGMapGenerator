package Abstract2D.Base.PathFindAndUnion

import Ancillary.Point

class Node(val value: Point, var rank: Int) {
  var parent: Node = this

  override def toString: String = {
    if (this == this.parent) "[v: " + value.toString + ", r: " + rank.toString + "]"
    else "[v: " + value.toString + ", r: " + rank.toString + ", p ->" + parent.toString + "]"
  }
}