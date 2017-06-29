package GUI

import com.sksamuel.scrimage.Image
import Abstract2D.Containers.{AllGrass, AllGround, AllPaths}
import Abstract2D.{Abstract2DMapGenerator, Map}
import Abstract2D.Objects._
import Abstract3D.Abstract3DMapGenerator
import Ancillary.Point
import Randomization.TileGenerator
import Generator.ImageCreator
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
    val mapParams = new MapParameters(20, 15, 20, 5)
    val map = new Abstract2DMapGenerator(mapParams).generateMap
    val piecesOfMap = new Abstract3DMapGenerator(map).generateMap()
    val piecesOfTile = new TileGenerator(piecesOfMap).generateMap()
    val image = new ImageCreator(mapParams.width, mapParams.height).addTiles(piecesOfTile)
    val out = new File("C:\\Users\\FAPFOOD\\Desktop\\Test.png")
    image
  }
}
