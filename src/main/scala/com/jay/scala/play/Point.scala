package com.jay.scala.play

class Point(x: Int, y: Int) {

  def isEven: Boolean = (x + y) % 2 == 0

  def isOdd: Boolean = (x + y) % 2 != 0

  override def toString: String = s"($x, $y)"


}
