package com.cbox.kioskservice.api.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cbox.kioskservice.api.order.domain.Order;
import com.cbox.kioskservice.api.order.repository.OrderItemRepository;
import com.cbox.kioskservice.api.order.repository.OrderRepository;
import com.cbox.kioskservice.api.payment.domain.Payment;
import com.cbox.kioskservice.api.payment.domain.PaymentStatus;
import com.cbox.kioskservice.api.payment.dto.PaymentDTO;
import com.cbox.kioskservice.api.payment.dto.PaymentRequestDTO;
import com.cbox.kioskservice.api.payment.dto.PaymentResponseDTO;
import com.cbox.kioskservice.api.payment.repository.PaymentRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentServiceImpl implements PaymentService{
    
    private final PaymentRepository paymentRepository;
    

    private final OrderRepository orderRepository;


    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO requestDTO) {
        
        Order order = orderRepository.findById(requestDTO.getOno()).orElseThrow(() -> new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
    
        Payment payment = Payment.builder()
                .order(order)
                .payMethod(requestDTO.getPaymentMethod())
                .payStatus(PaymentStatus.SUCCESS)
                .totalPrice(requestDTO.getTotalPrice())
                .transactionId(requestDTO.getTransactionId())
                .build();
        
        paymentRepository.save(payment);

        return toResponseDTO(payment);
    
    
    }

    @Override
    public PaymentResponseDTO getPaymentByOrder(Long ono) {
        
        Payment payment = paymentRepository.getPaymentOfOrder(ono);
        return toResponseDTO(payment);

    }

    @Override
    public PaymentResponseDTO getPaymentByTransactionId(String transactionId) {
       
        Payment payment = paymentRepository.findByTransactionId(transactionId).orElseThrow();
                    
        return toResponseDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getPaymentByStatus(PaymentStatus status) {
       
        List<Payment> paymentList = paymentRepository.findByPayStatus(status);
        return paymentList.stream().map(this::toResponseDTO).collect(Collectors.toList());

    }
    




    @Override
    public Long updatePaymentStatus(Long payno, PaymentStatus status) {
        
        Payment payment = paymentRepository.findById(payno).orElseThrow();

        log.info(payment);

        payment.setPayStatus(status);

        return paymentRepository.save(payment).getPayno();


    }


    private PaymentResponseDTO toResponseDTO(Payment payment){
        return PaymentResponseDTO.builder()
                .paymentId(payment.getPayno())
                .ono(payment.getOrder().getOno())
                .paymentMethod(payment.getPayMethod())
                .paymentStatus(payment.getPayStatus())
                .paidAmount(payment.getTotalPrice())
                .transactionId(payment.getTransactionId())
                .createdTime(payment.getRegDate())
                .build();
    }
 
    
}
