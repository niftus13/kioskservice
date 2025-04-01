package com.cbox.kioskservice.api.product.repository.search;

import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;
import com.cbox.kioskservice.api.product.dto.ProductListDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ProductListDTO> listWithCate(PageRequestDTO pageRequestDTO);
    
    PageResponseDTO<ProductListDTO> listWithSearchCate(PageRequestDTO pageRequestDTO);
}
