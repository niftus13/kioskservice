package com.cbox.kioskservice.productTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;
import com.cbox.kioskservice.api.product.dto.ProductDTO;
import com.cbox.kioskservice.api.product.dto.ProductListDTO;
import com.cbox.kioskservice.api.product.service.ProductService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductServiceTest {


    @Autowired
    ProductService productService;

    @Test
    public void readTest(){

        Long pno = 1L;

        ProductDTO dto = productService.readOne(pno);

        log.info(dto);
        
    }

    @Test
    public void readListTest(){

        PageRequestDTO dto =  new PageRequestDTO();

        PageResponseDTO<ProductListDTO> result = productService.list(dto);

        for(ProductListDTO dtos : result.getDtoList()){
            log.info(dtos);
        }
         
    }


    
}
