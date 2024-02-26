package com.example.easynotes.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class NotesEventConsumer {
    private final KafkaConsumer<String, String> consumer;
    // private final String topic;

    public NotesEventConsumer() {
        // this.topic = "notes-repo";
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Change to your Kafka broker address
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "notes-group"); // Consumer group id
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        this.consumer = new KafkaConsumer<>(props);
    }

    public void consumeEvents() {
        String topic = "notes-repo";
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("[+][+][+]Received message: " + record.value() + "[+][+][+]");
            }
        }
    }

    public void close() {
        consumer.close();
    }
}
