package com.cbox.kioskservice.api.payment.dto;

import java.time.LocalDateTime;

import com.cbox.kioskservice.api.payment.domain.PaymentMethod;
import com.cbox.kioskservice.api.payment.domain.PaymentStatus;

import lombok.Data;

@Data
public class PaymentDTO {

    private Long payno;
    
    private Long ono;

    private PaymentMethod payMethod;

    private PaymentStatus payStatus;

    private String transactionId;

    private int totalPrice;

    private LocalDateTime regDate;
    

}
