package com.cbox.kioskservice.api.payment.domain;

import com.cbox.kioskservice.api.common.baseEntity.BaseEntity;
import com.cbox.kioskservice.api.order.domain.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = "order")
@Table(
    name = "tbl_payment"
)
public class Payment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payno;

    @ManyToOne
    @JoinColumn(name = "Order_no", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod payMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus payStatus;
    
    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false, unique = true)
    private String transactionId;



}
