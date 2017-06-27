import java.io._

import Abstract2D.Containers.StreetNetwork
import Abstract2D.Map
import Abstract2D.Objects.{Signpost, Tree}
import Abstract3D.Abstract3DMapGenerator
import Ancillary.Point
import Randomization.TileGenerator
import Generator.MapGenerator

object Appl {
  def main(agrs: Array[String]) {

    val map = new Map(5, 5)
    map.addElement(new Signpost(Point(3, 4), Point(4, 4)))
    map.addElement(new Signpost(Point(-1, 0), Point(0, 0)))
    map.addElement(new Tree(List(Point(1, 1))))
    map.addElement(new Tree(List(Point(1, 2))))
    map.addElement(new Tree(List(Point(1, 3))))
    map.addElement(new Tree(List(Point(2, 1))))
    map.addElement(new Tree(List(Point(3, 3))))
    map.addElement(new StreetNetwork(map))
    val piecesOfMap = new Abstract3DMapGenerator(map).generateMap()
    val piecesOfTile = new TileGenerator(piecesOfMap).generateMap()

    val gen = new MapGenerator(5, 5)
    val out = new File("C:\\Users\\FAPFOOD\\Desktop\\Test.png")
    for (el <- piecesOfTile)
      gen.addTileOn(el._3, el._1)
    gen.image.output(out)

  }
}