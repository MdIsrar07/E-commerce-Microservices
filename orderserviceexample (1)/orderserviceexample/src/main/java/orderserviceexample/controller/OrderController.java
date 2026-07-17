package orderserviceexample.controller;

import orderserviceexample.dto.OrderEvent;
import orderserviceexample.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer producer;

    public OrderController(OrderProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String placeOrder(@RequestBody OrderEvent event){
        producer.sendOrderEvent(event);
        return "Order Event Published Succssfully";
    }
}
