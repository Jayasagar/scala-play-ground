package com.jay.scala.play.streams;

import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.kafka.ConsumerSettings;
import akka.kafka.ProducerSettings;
import akka.kafka.Subscriptions;
import akka.kafka.javadsl.Consumer;
import akka.stream.ActorAttributes;
import akka.stream.Supervision;
import akka.stream.javadsl.GraphDSL;
import akka.stream.javadsl.Source;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;

public class KafkaConsumerStream extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Throwable {
        Consumer.committableSource(getConsumerSettings(), Subscriptions.topics("test-topic"))
                .withAttributes(ActorAttributes.withSupervisionStrategy(new TestDecider()));

//        GraphDSL.create(builder -> {
//
//        });
    }

    public class TestWatchTerm implements Function2 {

        @Override
        public Object apply(Object arg1, Object arg2) throws Exception {
            System.out.println("TestWatchTerm" + arg1 + arg2);
            return null;
        }
    }

    public class KillItself implements Supervision.Directive {
        KillItself() {
            System.out.println("KillItself directive called");
            getSelf().tell(PoisonPill.getInstance(), getSelf());
        }
    }

    public class TestDecider implements Function<Throwable, Supervision.Directive> {

        @Override
        public Supervision.Directive apply(Throwable v1) {
            System.out.println("Going to call KillItself directive called" + v1.getMessage());
            return new KillItself();
        }
    }

    private ConsumerSettings<String, String> getConsumerSettings() {
        return  ConsumerSettings.create(getContext().system(), new StringDeserializer(), new StringDeserializer())
                .withBootstrapServers("localhost:9092")
                .withGroupId("test-group")
                .withProperty("max.poll.records", "10")
                .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    }

    private ProducerSettings<String, String> getProducerSettings() {
        return ProducerSettings.create(getContext().system(), new StringSerializer(), new StringSerializer())
                .withBootstrapServers("localhost:9092");
    }
}
