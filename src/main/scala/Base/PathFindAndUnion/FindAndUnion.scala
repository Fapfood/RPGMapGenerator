package Base.PathFindAndUnion

import Ancillary.Point

class FindAndUnion {
  def makeSet(value: Point): Node = {
    val n: Node = new Node(value, 0)
    n.parent = n
    n
  }

  def findSet(node: Node): Node = {
    if (node != node.parent)
      node.parent = findSet(node.parent)
    node.parent
  }

  def union(node1: Node, node2: Node): Unit = link(findSet(node1), findSet(node2))

  def link(node1: Node, node2: Node): Unit = {
    if (node1.rank > node2.rank)
      node2.parent = node1
    else {
      node1.parent = node2
      if (node1.rank == node2.rank)
        node2.rank = node2.rank + 1
    }
  }

}
