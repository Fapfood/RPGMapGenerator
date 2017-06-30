package Abstract2D.Ancillary

import Ancillary.Point

object BetweenPointsObject {

  def planIdealPath(startPoint: Point, endPoint: Point, balance: Double = 0.5): List[Point] = {
    if (balance < 0 || balance > 1)
      throw new Exception("Wrong balance")

    if (startPoint == endPoint)
      return List(startPoint)

    val paintLine = planPaintLine(startPoint, endPoint, balance)
    val likePaintList = paintLine.map(v => v._1)
    val forContinuity = paintLine.map(v => v._2)

    val listBuff = likePaintList.to[collection.mutable.ArrayBuffer]

    val operateOn_x = Math.abs(endPoint.x - startPoint.x) >= Math.abs(endPoint.y - startPoint.y)

    for (i <- 0 until likePaintList.length - 1) {
      val thisElem = likePaintList(i)
      val nextElem = likePaintList(i + 1)
      if (operateOn_x) {
        if (thisElem.y != nextElem.y) {
          if (forContinuity(i) < forContinuity(i + 1))
            listBuff += Point(thisElem.x, nextElem.y)
          else
            listBuff += Point(nextElem.x, thisElem.y)
        }
      } else {
        if (thisElem.x != nextElem.x) {
          if (forContinuity(i) < forContinuity(i + 1))
            listBuff += Point(nextElem.x, thisElem.y)
          else
            listBuff += Point(thisElem.x, nextElem.y)
        }
      }
    }

    def lt(p1: Point, p2: Point): Boolean = {
      //true if p1 < p2
      if (p1.x != p2.x) {
        if (endPoint.x - startPoint.x > 0) { //x rosną
          if (p1.x < p2.x)
            return true
        } else { //x maleją
          if (p1.x > p2.x)
            return true
        }
      } else {
        if (endPoint.y - startPoint.y > 0) { //y rosną
          if (p1.y < p2.y)
            return true
        } else { //y maleją
          if (p1.y > p2.y)
            return true
        }
      }
      false
    }

    listBuff.toList.sortWith(lt)
  }

  private def planPaintLine(startPoint: Point, endPoint: Point, balance: Double = 0.5): List[(Point, Double)] = {
    /** Przyjmuje dwa punkty i wagę przechylenia w kierunku jednego z nich
      * Zwraca listę tupli, gdzie:
      * pierwszy to punkt posortowany rosnąco po znaczącej współrzędnej
      * drugi to double oznaczający różnicę potrzebną do ciągłości
      * */

    if (balance < 0 || balance > 1)
      throw new Exception("Wrong balance")

    val delta_x = endPoint.x - startPoint.x
    val delta_y = endPoint.y - startPoint.y
    val operateOn_x = Math.abs(delta_x) >= Math.abs(delta_y)


    var lineEquation = (_: Double) => 0.0

    if (operateOn_x)
      lineEquation = f_Of_X(startPoint, endPoint)
    var start = startPoint.x
    var end = endPoint.x
    var negatedBalance = 1 - balance
    if (delta_x < 0) {
      start = endPoint.x
      end = startPoint.x
      negatedBalance = balance
    }
    if (!operateOn_x) {
      lineEquation = f_Of_Y(startPoint, endPoint)
      start = startPoint.y
      end = endPoint.y
      if (delta_y < 0) {
        start = endPoint.y
        end = startPoint.y
        negatedBalance = balance
      }
    }

    val arrBuff = collection.mutable.ArrayBuffer.empty[Double]
    for (i <- start to end)
      arrBuff += lineEquation(i)

    val determinants = arrBuff.map(e => e - e.floor - negatedBalance)

    val values = arrBuff.map(e => e.floor.toInt)
      .zip(determinants.map(e => if (e > 0) 1 else 0))
      .map(v => v._1 + v._2)

    val listBuff = collection.mutable.ListBuffer.empty[Point]
    for (i <- start to end)
      if (operateOn_x)
        listBuff += Point(i, values(i - start))
      else
        listBuff += Point(values(i - start), i)

    val forContinuity = determinants.map(e => Math.abs(e))
    listBuff.toList.zip(forContinuity)
  }

  private def f_Of_X(startPoint: Point, endPoint: Point): Double => Double = {
    if (endPoint.x == startPoint.x)
      throw new Exception("This equation is not a function")

    val a = (endPoint.y - startPoint.y).toDouble / (endPoint.x - startPoint.x)
    val b = endPoint.y - endPoint.x * a

    (x: Double) => a * x + b
  }

  private def f_Of_Y(startPoint: Point, endPoint: Point): Double => Double = {
    if (endPoint.y == startPoint.y)
      throw new Exception("This equation is not a function")

    val a = (endPoint.x - startPoint.x).toDouble / (endPoint.y - startPoint.y)
    val b = endPoint.x - endPoint.y * a

    (y: Double) => a * y + b
  }
}
