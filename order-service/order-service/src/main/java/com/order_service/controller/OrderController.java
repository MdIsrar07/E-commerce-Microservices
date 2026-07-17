package com.order_service.controller;

import com.order_service.entity.Order;
import com.order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // http://localhost:8084//api/v1/order/place

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(
          @RequestHeader("X-CART-ID") String uuid

    ){
        Order order=orderService.placeOrder(uuid);
        return ResponseEntity.ok(Map.of(
           "messege","Order successfully",
           "orderId",order.getId(),
           "totalAmount",order.getTotalAmount()

        ));

    }

    @PutMapping("/{orderId}")
    public Boolean updateOrderStatus(
          @PathVariable  long orderId)
    {
        Boolean status = orderService.markOrderStatus(orderId);
        return status;
    }
}
