package com.cbox.kioskservice.paymentTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cbox.kioskservice.api.order.domain.Order;
import com.cbox.kioskservice.api.order.domain.OrderItem;
import com.cbox.kioskservice.api.order.repository.OrderRepository;
import com.cbox.kioskservice.api.payment.domain.Payment;
import com.cbox.kioskservice.api.payment.domain.PaymentMethod;
import com.cbox.kioskservice.api.payment.domain.PaymentStatus;
import com.cbox.kioskservice.api.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@RequiredArgsConstructor
@Log4j2
public class PaymentRepTest {


    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;



    @Test
    public void insertPayment(){

        Long ono = 3L;

        Order order = orderRepository.findById(ono).orElseThrow();

        Payment payment = Payment.builder()
                            .order(order)
                            .payMethod(PaymentMethod.CARD)
                            .payStatus(PaymentStatus.SUCCESS)
                            .totalPrice(100000)
                            .transactionId("TXN_123456")
                            .build();

        log.info(payment);

        paymentRepository.save(payment);


    }




    @Test
    public void readPayment(){

        Long ono = 3L;


        

    }


    
    
}
