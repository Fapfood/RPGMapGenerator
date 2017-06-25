package GUI

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
  }

  def save(): Unit = {
    println("Saving file")
  }
}
