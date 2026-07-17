package com.product_service.dto;



import com.product_service.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class SubCategoryDto {

    private Integer id;
    private String name;

    private Set<Product> products = new LinkedHashSet<>();
}

