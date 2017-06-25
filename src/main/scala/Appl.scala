import Abstract2D.MapParameters

object Appl {
  def main(agrs: Array[String]): Unit = {
    println("Hello, world!")
    MapParameters.instance.width = 3;
    println(MapParameters.instance.width);
  }
}
