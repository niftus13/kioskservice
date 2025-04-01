package com.cbox.kioskservice.api.order.domain;

import com.cbox.kioskservice.api.admin.domain.Member;
import com.cbox.kioskservice.api.common.baseEntity.BaseEntity;
import com.cbox.kioskservice.api.payment.domain.Payment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "owner")
@Table(
    name = "tbl_order",
    indexes = {@Index(columnList = "member_owner", name="idx_order_kiosk")}
)

public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;

    @OneToOne
    @JoinColumn(name = "member_owner")
    private Member owner;

    @OneToOne(mappedBy = "order",
        cascade = CascadeType.PERSIST) 
    private Payment payment;
}
