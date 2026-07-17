package com.notification_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {
    private String mobile;
    private long orderId;
    private String email;
    private String status;
}
