package com.auth_login.config;

import com.auth_login.filter.JWTFilter;
import com.auth_login.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class AppSecurityConfi {
    
    @Autowired
    private JWTFilter jwtFilter;

   


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http    .csrf(csrf ->csrf.disable())
                .authorizeHttpRequests(
                req ->{
                    req.requestMatchers("/api/v1/auth/**")
                            .permitAll()
                            .requestMatchers("/api/v1/welcome/hello").hasAnyRole("USER","ADMIN")
                            .requestMatchers("/api/v1/welcome/hi").hasRole("ADMIN")
                            .anyRequest().authenticated();
                            
                }
        ).httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);;
        return http.build();
    }



    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return  config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (
          CustomerUserDetailsService customerUserDetailsService,
          PasswordEncoder passwordEncoder

    ){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(customerUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }




}
