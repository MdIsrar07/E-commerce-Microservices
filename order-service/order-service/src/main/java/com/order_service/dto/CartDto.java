package com.order_service.dto;


import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter @Setter
public class CartDto {

    private String uuid;
    private Long userId;
    private List<CartItemDto> items;
}
