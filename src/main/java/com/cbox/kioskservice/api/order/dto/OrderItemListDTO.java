package com.cbox.kioskservice.api.order.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class OrderItemListDTO {

    private Long oino;
    private int qty;  
    private Long pno;
    private String product_name;   
    private int price;  
    private String pfname;
    private LocalDateTime regDate;


    public OrderItemListDTO(Long oino, int qty, Long pno, String product_name, int price, String pfname,
            LocalDateTime regDate) {
        this.oino = oino;
        this.qty = qty;
        this.pno = pno;
        this.product_name = product_name;
        this.price = price;
        this.pfname = pfname;
        this.regDate = regDate;
    }


    


}
