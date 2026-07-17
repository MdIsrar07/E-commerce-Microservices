package com.payment_service.service;




import com.payment_service.client.PaymentFeignClient;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {


    private PaymentFeignClient paymentFeignClient;

    public PaymentService(PaymentFeignClient paymentFeignClient) {
        this.paymentFeignClient = paymentFeignClient;
    }

    public Session createCheckoutSession( Long orderId,BigDecimal amount) throws Exception {

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)

                        // ✅ Success URL (after payment)
                        .setSuccessUrl("http://localhost:8085/api/v2/payment/success?orderId=" + orderId)

                        // ❌ Cancel URL
                        .setCancelUrl("http://localhost:8085/cancel")

                        // ✅ Product info
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("USD")
                                                        .setUnitAmount(
                                                                amount.multiply(BigDecimal.valueOf(100)).longValue()
                                                        )
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Order Payment #" + orderId)
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        return Session.create(params);
    }

    public boolean markOrderAsPaid(Long orderId) {
        boolean status = paymentFeignClient.updateOrderStatus(orderId);
        return status;
    }
}
