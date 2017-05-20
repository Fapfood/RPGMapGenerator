package main.scala

class House(val widthLimit: Int, val heightLimit: Int, val floorArea: Int, val wallHeight: Int) {
  Shape.randomShape() match {
    case Shape.T => "zero"
    case Shape.L => "one"
    case Shape.+ => "two"
    case Shape.- => "many"
    case Shape.| => "many"
  }

}
