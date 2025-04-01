package com.cbox.kioskservice.api.product.domain;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


@Entity
@Table(name = "products",
        indexes = {@Index(name = "category_idx", columnList = "category")} )
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    @NotNull
    private String pname;

    @NotNull
    private int price;

    @Column(length = 200)
    private String pdesc;

    private Long inventory;

    private boolean soldout;

    private boolean delFlag;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    public void ChangeCategory(ProductCategory category){
        this.category = category;
    }


    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();


    public void addImage(String name){

        ProductImage pImage = ProductImage.builder().pfname(name)
            .ord(images.size()).build();

        images.add(pImage);

    }

    public void clearImages(){
        images.clear();
    }


    public void changePrice(int price){
        this.price = price;
    }

    public void changepname(String pname){
        this.pname = pname;
    }

    public void changeSold(boolean soldout){
        this.soldout = soldout;
    }

    public void changeDel(boolean delFlag){
        this.delFlag = delFlag;
    }

    public void changeInventory(Long inventory){
        this.inventory = inventory;
    }

    
}
