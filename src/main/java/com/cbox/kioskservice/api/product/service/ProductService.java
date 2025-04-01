package com.cbox.kioskservice.api.product.service;

import org.springframework.transaction.annotation.Transactional;

import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;
import com.cbox.kioskservice.api.product.dto.ProductDTO;
import com.cbox.kioskservice.api.product.dto.ProductListDTO;

@Transactional
public interface ProductService {
    
    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductListDTO> listCate(PageRequestDTO pageRequestDTO);

    Long register(ProductDTO productDTO);

    ProductDTO readOne(Long pno);

    void remove(Long pno);

    void modify(ProductDTO productDTO);

}
