package com.cbox.kioskservice.api.payment.dto;

import com.cbox.kioskservice.api.payment.domain.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDTO {
    // 주문 ID(orderId)
    private Long ono; 

    private PaymentMethod paymentMethod;

    private int totalPrice;

    private String transactionId;
}
