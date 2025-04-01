package com.cbox.kioskservice.api.order.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    
    private String id;

    private Long pno;

    private int qty;

    private Long oino;
}
