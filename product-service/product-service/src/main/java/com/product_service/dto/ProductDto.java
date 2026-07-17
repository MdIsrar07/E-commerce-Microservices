package com.product_service.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class ProductDto {

    private long id;
    private String name;
    private SubCategoryDto subCategoryDto;
    private Set<BrandDto> brands = new LinkedHashSet<>();
}

