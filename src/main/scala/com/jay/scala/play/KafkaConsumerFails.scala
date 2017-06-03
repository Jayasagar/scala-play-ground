package com.jay.scala.play

import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, ProducerSettings}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer}

object KafkaConsumerFails {

  def main(args: Array[String]): Unit = {
    println("Hello");
  }

  def actorSystem: ActorSystem = ActorSystem.create("Kafka-Test-System")

  def defaultProducerSettings: ProducerSettings[String, Array[Byte]] = ProducerSettings
    .create(actorSystem, new StringSerializer, new ByteArraySerializer)
    .withBootstrapServers("localhost:9092")

  def defaultConsumerSettings: ConsumerSettings[String, Array[Byte]] = {
    val consumerSettings = ConsumerSettings
      .create(actorSystem, new StringDeserializer, new ByteArrayDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("consumer-group")
      .withProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    consumerSettings
  }

}
