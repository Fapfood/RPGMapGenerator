package Abstract3D

object Abstract3DTileType extends Enumeration {
  type Abstract3dTileType = Value
  val
  Layer0_Ground,
  Layer0_Grass,
  Layer0_Sand,
  Layer0_Path,
  Layer0_Road,
  Layer0_Decoration,

  Layer1_Wall,
  Layer1_Roof,
  Layer1_Tree,
  Layer1_DoubleTree,
  Layer1_Signpost,
  Layer1_Decoration,

  Layer2_Window,
  Layer2_Door,
  Layer2_Tree,
  Layer2_DoubleTree,
  Layer2_Decoration,

  Layer3_Decoration
  = Value
}