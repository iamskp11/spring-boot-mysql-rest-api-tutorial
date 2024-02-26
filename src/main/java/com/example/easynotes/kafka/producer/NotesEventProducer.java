package com.example.easynotes.kafka.producer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class NotesEventProducer {
    private final KafkaProducer<String, String> producer;
    // private final String topic;

    public NotesEventProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // Change to your Kafka broker address
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    public void sendEvent(String message) {
        String topic = "notes-repo";
        producer.send(new ProducerRecord<>(topic, message));
    }

    public void close() {
        producer.close();
    }
}
