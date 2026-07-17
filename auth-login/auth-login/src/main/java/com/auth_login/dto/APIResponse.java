package com.auth_login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class APIResponse <T>{
    private String message;
    private int status;
    private T data;
}
