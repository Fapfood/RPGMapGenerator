package GUI

import Abstract2D.MapParameters

import scala.swing.TextField

object Controller {
  val r = scala.util.Random
  val WidthRange : Range = Range(300,600)
  val HeightRange : Range = Range(300, 600)
  val TreesRange : Range = Range(0,25)
  val HousesRange : Range = Range(0,10)
  val ExitsRange : Range = Range(0,5)
  val TextRangeMap = Map(MainWindow.ExitsTextField -> ExitsRange,
    MainWindow.HeightTextField -> HeightRange,
    MainWindow.HousesTextField -> HousesRange,
    MainWindow.TreesTextField -> TreesRange,
    MainWindow.WidthTextField -> WidthRange)
  def randomize(): Unit = {
    println("Randomizing")
  for((k,v) <- TextRangeMap) k.text = (v.min + (r.nextInt(v.max - v.min) + 1)).toString

  }

  def generate(): Unit = {
    println("Generating image")
    val params = new MapParameters(width, height, treesNumber, housesNumber, exitsNumber)
    //todo: call method which will return image, then print the image.
  }

  def save(): Unit = {
    println("Saving file")
  }

  def width : Int = {
    MainWindow.WidthTextField.text.toInt
  }
  def height : Int = {
    MainWindow.HeightTextField.text.toInt
  }
  def treesNumber : Int = {
    MainWindow.TreesTextField.text.toInt
  }
  def housesNumber : Int = {
    MainWindow.HousesTextField.text.toInt
  }
  def exitsNumber : Int = {
    MainWindow.ExitsTextField.text.toInt
  }
}
