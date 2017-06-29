package GUI

import com.sksamuel.scrimage.Image
import Abstract2D.Containers.{AllGrass, AllGround, AllPaths}
import Abstract2D.Map
import Abstract2D.Objects._
import Abstract3D.Abstract3DMapGenerator
import Ancillary.Point
import Randomization.TileGenerator
import Generator.MapGenerator
import java.io._
import javax.swing.ImageIcon

import scala.swing.{Dialog, FileChooser}

object Controller {
  val r = scala.util.Random
  val WidthRange: Range = Range(300, 600)
  val HeightRange: Range = Range(300, 600)
  val TreesRange: Range = Range(0, 25)
  val HousesRange: Range = Range(0, 10)
  val ExitsRange: Range = Range(0, 5)
  val TextRangeMap = Map(MainWindow.ExitsTextField -> ExitsRange,
    MainWindow.HeightTextField -> HeightRange,
    MainWindow.HousesTextField -> HousesRange,
    MainWindow.TreesTextField -> TreesRange,
    MainWindow.WidthTextField -> WidthRange)

  def randomize(): Unit = {
    println("Randomizing")
    for ((k, v) <- TextRangeMap) k.text = (v.min + (r.nextInt(v.max - v.min) + 1)).toString

  }

  def generate(): Unit = {
    println("Generating image")
    val params = new MapParameters(width, height, treesNumber, housesNumber, exitsNumber)
    //todo: call method which will return image, then print the image.
    val image: Image = createDebugMap()
    val out: File = new File("tmp.png")
    image.output(out)

    val img = new ImageIcon("tmp.png")
    val res = Dialog.showConfirmation(message = null, icon = img, title = "Do you want to save this image?")
    if (res == Dialog.Result.Ok) {
      save(image)
    }
  }

  def save(image: Image): Unit = {
    val chooser = new FileChooser(null)
    //chooser.fileSelectionMode = FileChooser.SelectionMode.DirectoriesOnly
    val result = chooser.showSaveDialog(null)
    if (result == FileChooser.Result.Approve) {
      image.output(chooser.selectedFile)
    }
  }

  def width: Int = {
    MainWindow.WidthTextField.text.toInt
  }

  def height: Int = {
    MainWindow.HeightTextField.text.toInt
  }

  def treesNumber: Int = {
    MainWindow.TreesTextField.text.toInt
  }

  def housesNumber: Int = {
    MainWindow.HousesTextField.text.toInt
  }

  def exitsNumber: Int = {
    MainWindow.ExitsTextField.text.toInt
  }

  private def createDebugMap(): Image = {
    val map = new Map(25, 25)
    map.addElement(new AllGrass(new Grass(Point(0, 0), Point(24, 24)), new AllGround(List.empty[Ground])))
    map.addElement(new Signpost(Point(3, 4), Point(4, 4)))
    map.addElement(new Signpost(Point(-1, 0), Point(0, 0)))
    map.addElement(new Tree(List(Point(1, 1))))
    map.addElement(new Tree(List(Point(1, 2))))
    map.addElement(new Tree(List(Point(1, 3))))
    map.addElement(new Tree(List(Point(2, 1))))
    map.addElement(new Tree(List(Point(3, 3))))
    map.addElement(new Building(Point(6, 6), Point(9, 8), map.pointsList, 2))
    map.addElement(new AllPaths(map))
    val piecesOfMap = new Abstract3DMapGenerator(map).generateMap()
    val piecesOfTile = new TileGenerator(piecesOfMap).generateMap()

    val gen = new MapGenerator(25, 25)
    for (el <- piecesOfTile)
      gen.addTileOn(el._3, el._1)
    gen.image
  }
}
