package com.jay.scala.play.actor

import akka.actor.{ActorRef, ActorSystem, Props}

object BookingApp extends App {

  val actorSystem = ActorSystem("BookAMovie")

  val payment: ActorRef = actorSystem.actorOf(Props[Payment], "payment")
  val movieActorRef:ActorRef = actorSystem.actorOf(Props(classOf[Booking], payment), "bookMovie")
  val user:ActorRef = actorSystem.actorOf(Props(classOf[User], movieActorRef), "booking")


  user ! BookTollywood("SuperMovie")

  user ! BookTollywood("SomeMovie")

  user ! BookHollywood("Next")

  // tell TollywoodMovieRequest: send a message to actor

}
