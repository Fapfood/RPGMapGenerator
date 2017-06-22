package Abstract2D

class MapVisualizer {
  def dump(board: collection.immutable.Map[Point, String]): String = {
    val minX = board.keys.map(_.x).min
    val maxX = board.keys.map(_.x).max
    val minY = board.keys.map(_.y).min
    val maxY = board.keys.map(_.y).max
    val builder = new StringBuilder

    //firstLine
    builder.append("y\\x  ")
    for (j <- minX to maxX) builder.append("%2d".format(j % 100))
    builder.append(System.lineSeparator)

    //secondLine
    builder.append("%2d: ".format((minY - 1) % 100))
    for (_ <- minX to maxX) builder.append("--")
    builder.append("-")
    builder.append(System.lineSeparator)

    //mainMap
    for (i <- minY to maxY) {
      builder.append("%2d: |".format(i % 100))
      for (j <- minX to maxX) {
        if (board.contains(Point(j, i))) builder.append(board(Point(j, i)))
        else builder.append(" ")
        builder.append("|")
      }
      builder.append(System.lineSeparator)
    }

    //lastLine
    builder.append("%2d: ".format((maxY + 1) % 100))
    for (_ <- minX to maxX) builder.append("--")
    builder.append("-")
    builder.append(System.lineSeparator)

    builder.toString
  }
}
