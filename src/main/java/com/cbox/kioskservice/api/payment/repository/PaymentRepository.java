package com.cbox.kioskservice.api.payment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbox.kioskservice.api.payment.domain.Payment;
import com.cbox.kioskservice.api.payment.domain.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

    // 쿼리 
    @Query("select p from Payment p where p.order.ono = :ono")
    public Payment getPaymentOfOrder(@Param("ono") Long ono);

    // jpa


    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByPayStatus(PaymentStatus status);


    
}
