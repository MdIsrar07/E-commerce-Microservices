package com.order_service.service;

import com.order_service.client.CartFeignClient;
import com.order_service.dto.CartDto;
import com.order_service.dto.CartItemDto;
import com.order_service.entity.Order;
import com.order_service.entity.OrderItem;
import com.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private  final CartFeignClient cartFeignClient;
    private final OrderRepository orderRepository;

    public OrderService(CartFeignClient cartFeignClient, OrderRepository orderRepository) {
        this.cartFeignClient = cartFeignClient;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(String uuid){
        CartDto cart=cartFeignClient.getCart(uuid);

        if (cart ==null || cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        // Create Order
        Order order=new Order();
        order.setCartUuid(uuid);
        order.setUserId(cart.getUserId());
        order.setStatus("CREATED");

        BigDecimal total=BigDecimal.ZERO;

        for (CartItemDto cartItem : cart.getItems()){
            OrderItem orderItem=new OrderItem();

            orderItem.setProductId(cartItem.getProductId());
            orderItem.setBrandId(cartItem.getBrandId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
            total=total.add(
                    cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }
        order.setTotalAmount(total);

        // 4
        Order saveOrder=orderRepository.save(order);
        // 5
        cartFeignClient.clearCart(uuid);
        return saveOrder;
    }

    public Boolean markOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus("completed");
        Order saved = orderRepository.save(order);
        if (order.getStatus().equals("completed")){
            return  true;
        }
        return false;
    }
}
