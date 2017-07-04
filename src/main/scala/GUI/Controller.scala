package GUI

import java.io._
import javax.swing.ImageIcon

import Generators.MapGenerator
import com.sksamuel.scrimage.Image

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

  private var tmpCounter: Int = 0

  def generate(): Unit = {
    println("Generating image")
    val params = new MapParameters(width, height, treesNumber, housesNumber, exitsNumber)

    val image: Image = MapGenerator.createMapWithParameters(params)
    val tmpFileName = "tmp" + tmpCounter.toString + ".png"
    tmpCounter = tmpCounter + 1
    val out: File = new File(tmpFileName)
    image.output(out)

    val img = new ImageIcon(tmpFileName)
    val res = Dialog.showConfirmation(message = null, icon = img, title = "Do you want to save this image?")
    if (res == Dialog.Result.Ok) {
      save(image)
    }
    out.delete()
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

}
