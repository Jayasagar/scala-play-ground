package com.jay.scala.play.actor

import akka.actor.Actor

import scala.util.Random

case class MakePayment(amount: Int)
case object Confirm

class PaymentTransactionFailed(msg: String) extends RuntimeException

class Payment extends Actor {
  override def receive: Receive = {
    case MakePayment(amount: Int) =>
      if (Random.nextBoolean()) {
        throw new PaymentTransactionFailed("Payment was unsuccessfull")
      }

      println("Payment done")
      sender ! Confirm
  }
}
