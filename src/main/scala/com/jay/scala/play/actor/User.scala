package com.jay.scala.play.actor

import akka.actor.{Actor, ActorRef}

case class BookTollywood(name: String)
case class BookHollywood(name: String)

class User(bookingRef: ActorRef) extends Actor {

  override def receive: Receive = {
    case BookTollywood(name: String) =>
        println("Movie Request" + name)
        bookingRef ! TollywoodMovieRequest
    case BookHollywood(name: String) =>
        println("Movie Request" + name)
        bookingRef ! HollywoodMovieRequest
    case Bill(cost: Int) =>
        println("Pay your bill " + cost)
        bookingRef ! PayMyBill(cost)
  }
}
