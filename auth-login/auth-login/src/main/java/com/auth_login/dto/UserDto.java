package com.auth_login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private long id;
    private String name;
    private String username;
    private String email;
    private String password;

}
