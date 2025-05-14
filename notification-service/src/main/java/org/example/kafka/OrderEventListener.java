
package org.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @KafkaListener(topics = "orders", groupId = "notification-service")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("[NOTIFICATION] New order event received: " + record.value());
    }
}
