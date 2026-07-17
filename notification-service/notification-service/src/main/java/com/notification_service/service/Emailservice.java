package com.notification_service.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Emailservice{

    private JavaMailSender javaMailSender;

    public Emailservice(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendingEmail(String to,String subject,String message){
        SimpleMailMessage sm=new SimpleMailMessage();
        sm.setTo(to);
        sm.setSubject(subject);
        sm.setText(message);
        javaMailSender.send(sm);

    }
}
