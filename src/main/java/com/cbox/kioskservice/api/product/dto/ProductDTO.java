package com.cbox.kioskservice.api.product.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cbox.kioskservice.api.product.domain.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    
    private Long pno;
    private String product_name;
    private int price;
    private String desc;
    private Long inventory;
    private boolean soldout;
    private boolean delFlag;
    private ProductCategory category;



    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

    

    
}
