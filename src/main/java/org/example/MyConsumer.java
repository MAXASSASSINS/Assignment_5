package org.example;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.UnifiedJedis;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyConsumer {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis(System.getenv("REDIS_URL"));
//        UnifiedJedis jedis = new UnifiedJedis("redis://172.20.0.158:6379");

        Properties props = new Properties();
        props.put("bootstrap.servers", System.getenv("KAFKA_BOOTSTRAP_SERVERS"));
//        props.put("bootstrap.servers", "172.20.0.158:9092");
        props.put("group.id", System.getenv("GROUP_ID"));
//        props.put("group.id", "test-group-v2");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");

        Consumer<String, String> consumer = new KafkaConsumer<>(props);
//        consumer.subscribe(Collections.singletonList("as3_topic"));
        consumer.subscribe(Collections.singletonList(System.getenv("TOPIC_NAME")));

        System.out.println("Listening for messages...");
        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Received message: timestamp=%d, key=%s, value=%s, partition=%d, offset=%d%n",
                        record.timestamp(), record.key(), record.value(), record.partition(), record.offset());

                String oldTimeStamp = jedis.get(record.key());
                String newTimeStamp = record.value();
                if(oldTimeStamp == null || newTimeStamp.compareTo(oldTimeStamp) > 0){
                    jedis.set(record.key(), newTimeStamp);
                }
            }
        }
    }
}
