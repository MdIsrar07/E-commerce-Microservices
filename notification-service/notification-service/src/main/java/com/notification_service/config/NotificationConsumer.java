package com.notification_service.config;

import com.notification_service.dto.OrderEvent;
import com.notification_service.service.Emailservice;
import com.notification_service.service.SmsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    private Emailservice emailservice;
    private SmsService smsservice;

    public NotificationConsumer(Emailservice emailservice, SmsService smsservice ) {
        this.emailservice = emailservice;
        this.smsservice=smsservice;
    }

    @KafkaListener(
           topics = "order-events",
            groupId ="notification-group"

    )
    public void consume(OrderEvent event){
        System.out.println("Event Received :"+event.getEmail());

        if(event.getStatus().equals("success")) {
            emailservice.sendingEmail(event.getEmail(), "Translation completed", "your is placed successfully"+event.getOrderId());
            smsservice.sendSms(event.getMobile(),"Translation completed");
        }else{
            emailservice.sendingEmail(event.getEmail(), "Translation failed", "your is not placed successfully"+event.getOrderId());
            smsservice.sendSms(event.getMobile(),"Translation Incompleted");
        }

        String subject ="Order Status Update";
        String body="Order_ID:"+event.getOrderId()+"\nStatus :"+event.getStatus();

    }
}
