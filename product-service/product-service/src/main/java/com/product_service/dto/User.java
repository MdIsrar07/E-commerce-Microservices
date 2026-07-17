package com.product_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;
    private String name;
    private  String username;
    private String password;
    private String email;
    private String role;

}
