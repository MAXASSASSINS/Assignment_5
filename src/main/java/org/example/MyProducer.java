package org.example;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.protocol.types.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;

public class MyProducer {

    private static String getTimeStamp(){
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        long timestamp = zdt.toInstant().toEpochMilli();

        return String.valueOf(timestamp);
    }

    public static void main(String[] args) {
        RandomName randomName = new RandomName();

        Properties props = new Properties();
        props.put("bootstrap.servers", System.getenv("KAFKA_BOOTSTRAP_SERVERS"));
//        props.put("bootstrap.servers", "172.20.0.158:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        try{
            while(true){
                String key = randomName.getRandomName();
                String value = getTimeStamp();
                producer.send(new ProducerRecord<>(System.getenv("TOPIC_NAME"), key, value));
                System.out.println(key + "\t" + value);
                Thread.sleep(60 * 1000);
            }
        }
        catch (InterruptedException e){
            System.out.println("Interrupted");
        }

    }
}
