package com.cbox.kioskservice.api.payment.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbox.kioskservice.api.payment.domain.PaymentStatus;
import com.cbox.kioskservice.api.payment.dto.PaymentRequestDTO;
import com.cbox.kioskservice.api.payment.dto.PaymentResponseDTO;
import com.cbox.kioskservice.api.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("api/payments/")
@RequiredArgsConstructor
@Log4j2
public class PaymentController {


    private final PaymentService paymentService;
    
    
    @GetMapping("{ono}")
    public PaymentResponseDTO getPaymentByOrder(@PathVariable("ono") Long ono){

        log.info("order num : "+ono);

        return paymentService.getPaymentByOrder(ono);
    }


    @PostMapping
    public Map<PaymentResponseDTO, Long> createPayment(PaymentRequestDTO paymentRequestDTO){
        
        log.info(paymentRequestDTO);

        PaymentResponseDTO responseDTO = paymentService.createPayment(paymentRequestDTO);

        log.info(paymentRequestDTO.getOno() + "번 주문"+" 결제 생성");

        return Map.of(responseDTO, responseDTO.getPaymentId());
    }


    @PatchMapping("{paymentId}/status")
    public Long updatePaymentStatus(@PathVariable Long paymentId, @RequestParam String status){

        log.info(status);

        try {
            PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());

            return paymentService.updatePaymentStatus(paymentId, paymentStatus);
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("유효하지 않은 결제 상태 값입니다: " + status);
        }
    }



    
}
