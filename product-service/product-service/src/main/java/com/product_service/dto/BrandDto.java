package com.product_service.dto;


import com.product_service.entity.Image;
import com.product_service.entity.Product;
import com.product_service.entity.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class BrandDto {
    private long id;
    private String name;
    private BigDecimal price;

    private Product product;
    private Set<Size> sizes = new LinkedHashSet<>();
    private Set<Image> images = new LinkedHashSet<>();
}
