package com.jay.scala.play

object HelloWorld {

  def main(args: Array[String]): Unit = {
    println("Hello Scala")

    method1("Scala")

    println(m1(12, 10))

    println(m3(9))
  }

  def m1(age:Int, weight: Int): Int = age * weight

  def m2(age:Int, weight: Int): Int = {
    age * weight
  }

  val m3 = (x: Int) => x + 1

  def method1(name: String): Unit = {
    println("Name:" + name)
  }
}
