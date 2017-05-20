package Abstract3D

import Abstract3D.Abstract3DTileType.Abstract3dTileType

class MapField(val x: Int, val y: Int) {
  private var list = List[Abstract3dTileType]()

  def +(model: Abstract3dTileType): List[Abstract3dTileType] = {
    list = model :: list
    list
  }

  override def toString: String = list.toString()

  def get(index: Int): Abstract3dTileType = list(index)
}
