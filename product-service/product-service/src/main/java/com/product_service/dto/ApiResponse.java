package com.product_service.dto;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private int status;
    private T data;

}
