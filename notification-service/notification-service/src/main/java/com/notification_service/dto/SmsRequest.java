package com.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsRequest {
    private String to;
    private String message;
}
