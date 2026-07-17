package com.payment_service.controller;

import com.payment_service.dto.OrderEvent;
import com.payment_service.kafka.OrderProducer;
import com.payment_service.service.PaymentService;
import com.stripe.model.checkout.Session;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private OrderProducer orderProducer;
    private PaymentService paymentService;

    public PaymentController(OrderProducer orderProducer, PaymentService paymentService) {
        this.orderProducer = orderProducer;
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout/{orderId}")
     public String createPaymentSession(@PathVariable Long orderId) throws Exception {

       Session session=paymentService.createCheckoutSession(orderId, BigDecimal.valueOf(2444L));

       return session.getUrl();
     }


     @GetMapping("/success")
     public String paymentSuccess(@RequestParam Long orderId){
         boolean status = paymentService.markOrderAsPaid(orderId);

         OrderEvent event=new OrderEvent();
         event.setOrderId(orderId);
         event.setEmail("mdisrar452@gmail.com");
         event.setMobile("7557780219");
         event.setStatus("success");
         orderProducer.sendOrderEvent(event);
             return "Payment successful  for orderId"+ orderId;


     }
}
