package com.jay.scala.play.actor

import akka.actor.SupervisorStrategy.{Decider, Resume}
import akka.actor.{Actor, ActorRef, OneForOneStrategy, SupervisorStrategy}

sealed trait MovieRequest
case object HollywoodMovieRequest extends MovieRequest
case object TollywoodMovieRequest extends MovieRequest
case class Bill(cost:Int)
case class PayMyBill(amount: Int)


class Booking(payment: ActorRef) extends Actor {

  final val paymentFailureDecider : Decider = {
    case _: PaymentTransactionFailed => Resume
  }

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy()(paymentFailureDecider.orElse(SupervisorStrategy.defaultStrategy.decider))

  override def receive: Receive = {
    case TollywoodMovieRequest => println("Request take to book tollywood movie")
      sender ! Bill(75)
    case HollywoodMovieRequest => println("Request taken to book hollywood movie")
      sender ! Bill(100)
    case PayMyBill(cost: Int) =>
      println("Pay amount!")
      payment ! MakePayment(cost)
    case Confirm => println("Booked your movie")
  }

}
