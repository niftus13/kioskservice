package com.cbox.kioskservice.productTests;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;
import com.cbox.kioskservice.api.product.domain.Product;
import com.cbox.kioskservice.api.product.domain.ProductCategory;
import com.cbox.kioskservice.api.product.dto.ProductListDTO;
import com.cbox.kioskservice.api.product.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepTest {


    @Autowired
    private ProductRepository productRepository;


    @Test
    public void testInsert(){

        ProductCategory[] categories = ProductCategory.values();

        for(int i=1; i<=100; i++){

            Product product = Product.builder()
                .product_name("Product.."+i)
                .price(i*100)
                .pdesc("Product.."+i+"번")
                .inventory(Long.valueOf(i))
                .category(categories[i%3])
                .build();

            product.addImage(UUID.randomUUID().toString()+"_aaa.jpg");
            product.addImage(UUID.randomUUID().toString()+"_bbb.jpg");
            product.addImage(UUID.randomUUID().toString()+"_ccc.jpg");
            productRepository.save(product);

        }

    }

    @Test
    @Transactional
    public void pOne(){

        Optional<Product> product = productRepository.findById(1L);
        log.info("________________________________");
        log.info(product);
        log.info("________________________________");
        System.out.println(product);

    }


    @Test
    public void pOne2(){

        Product product = productRepository.selectOne(1L);

        log.info(product);
    }   
    

    @Test
    @Transactional
    public void pList(){

        List<Product> product = productRepository.findAll();

        for(Product result : product){
            log.info(result);
        }
    }


    
    @Test
    public void psList(){

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListDTO> result = productRepository.list(requestDTO);

        for (ProductListDTO dto : result.getDtoList()) {
            log.info(dto);
        }
    }

    @Test
    public void psListWithcat(){

        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResponseDTO<ProductListDTO> result = productRepository.listWithCate(requestDTO);

        for (ProductListDTO dto : result.getDtoList()) {
            log.info(dto);
        }
    }


    
}
