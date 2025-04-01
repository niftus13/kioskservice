package com.cbox.kioskservice.api.payment.service;

import java.util.List;

import com.cbox.kioskservice.api.payment.domain.Payment;
import com.cbox.kioskservice.api.payment.domain.PaymentStatus;
import com.cbox.kioskservice.api.payment.dto.PaymentDTO;
import com.cbox.kioskservice.api.payment.dto.PaymentRequestDTO;
import com.cbox.kioskservice.api.payment.dto.PaymentResponseDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface PaymentService {

    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO);

    public PaymentResponseDTO getPaymentByOrder(Long ono);

    public PaymentResponseDTO getPaymentByTransactionId(String transactionId);

    public List<PaymentResponseDTO> getPaymentByStatus(PaymentStatus status);

    public Long updatePaymentStatus(Long payno, PaymentStatus status);

    
}
