package com.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-service", url = "http://localhost:8084")
public interface PaymentFeignClient {

        @PutMapping("/api/v1/order/{orderId}")
        boolean updateOrderStatus(@PathVariable("orderId") long orderId);


}
