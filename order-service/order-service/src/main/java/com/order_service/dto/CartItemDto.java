package com.order_service.dto;



import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter @Setter
public class CartItemDto {

    private Long productId;
    private Long brandId;
    private Integer quantity;
    private BigDecimal price;


}
