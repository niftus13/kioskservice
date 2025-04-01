package com.cbox.kioskservice.api.payment.dto;

import java.time.LocalDateTime;

import com.cbox.kioskservice.api.payment.domain.PaymentMethod;
import com.cbox.kioskservice.api.payment.domain.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    
    private Long paymentId;

    private Long ono;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private int paidAmount;

    private String transactionId;

    private LocalDateTime createdTime;
}
