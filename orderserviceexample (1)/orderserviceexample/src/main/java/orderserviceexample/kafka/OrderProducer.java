package orderserviceexample.kafka;

import orderserviceexample.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplatel;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplatel) {
        this.kafkaTemplatel = kafkaTemplatel;
    }
    private static final String TOPIC="order-events";

    public void sendOrderEvent(OrderEvent event){
        kafkaTemplatel.send(TOPIC,event);
        System.out.println("Event Sent :"+event);
    }
}
