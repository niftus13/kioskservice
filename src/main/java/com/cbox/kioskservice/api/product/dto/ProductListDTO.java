package com.cbox.kioskservice.api.product.dto;

import com.cbox.kioskservice.api.product.domain.ProductCategory;

import lombok.Data;

@Data
public class ProductListDTO {

    private Long pno;

    private String product_name;

    private String pdesc;

    private int price;

    private Long inventory;

    private boolean soldout;

    private String UUID;
    
    private String pfname;

    private ProductCategory category;

    private boolean delFlag;

    
    
}
