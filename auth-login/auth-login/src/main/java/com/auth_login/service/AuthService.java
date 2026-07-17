package com.auth_login.service;

import com.auth_login.dto.APIResponse;
import com.auth_login.dto.UserDto;
import com.auth_login.entity.User;
import com.auth_login.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public APIResponse<String> register(UserDto dto, String role){
        if (userRepository.existsByUsername(dto.getUsername())){
            APIResponse<String> response=new APIResponse<>();
            response.setMessage("registration Failed");
            response.setStatus(500);
            response.setData("user With username exits");
            return  response;
        }
        if (userRepository.existsByEmail(dto.getEmail())){
            APIResponse<String> response=new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("user With email exits");
            return response;
        }
        User user=new User();
        user.setRole(role);
        BeanUtils.copyProperties(dto,user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        APIResponse<String> response=new APIResponse<>();
        response.setMessage("Registration successful");
        response.setStatus(201);
        response.setData("Transactions completed");
        return response;
    }

}
