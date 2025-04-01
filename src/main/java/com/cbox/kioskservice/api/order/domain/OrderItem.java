package com.cbox.kioskservice.api.order.domain;

import com.cbox.kioskservice.api.product.domain.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "tbl_orderItem", indexes = {
    @Index(columnList = "order_ono", name = "idx_orderItem_order"),
    @Index(columnList = "product_pno", name = "idx_orderItem_pno_order")
})
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oino;

    @ManyToOne
    @JoinColumn(name = "product_pno")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "Order_ono")
    private Order order;

    private int qty;

    public void changeQty(int qty){
        this.qty = qty;
    }

    

    
}
