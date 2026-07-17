package com.product_service.dto;


import com.product_service.entity.SubCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class CategoryDto {


    private Integer id;
    private String name;
    private Set<SubCategory> subCategories ;
}

