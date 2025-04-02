package com.cbox.kioskservice.api.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cbox.kioskservice.api.common.file.FileUploader;
import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;
import com.cbox.kioskservice.api.product.dto.ProductDTO;
import com.cbox.kioskservice.api.product.dto.ProductListDTO;
import com.cbox.kioskservice.api.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/api/product/")
@RequiredArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService productService;

    private final FileUploader fileUploader;

    @GetMapping("list")
    public PageResponseDTO<ProductListDTO> list (PageRequestDTO pageRequestDTO){
        log.info(pageRequestDTO);

        return productService.listCate(pageRequestDTO);
    }



    @GetMapping("{pno}")
    public ProductDTO getOne(@PathVariable("pno") Long pno){
        log.info("pno............."+pno);
        return productService.readOne(pno);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return  fileUploader.getFile(fileName);
    }
    

    @PostMapping("")
    public Map<String,Long> register(ProductDTO productDTO){
        log.info(productDTO);

        List<String> fileNames = fileUploader.uploadFiles(productDTO.getFiles(), true);
        productDTO.setImages(fileNames);

        Long pno = productService.register(productDTO);

        return Map.of("result",pno);

    }


    @DeleteMapping("{pno}")
    public Map<String, Long> remove(@PathVariable("pno") Long pno){

        log.info("remove............"+ pno);

        productService.remove(pno);

        return Map.of("result",pno);
        
    }

    @PostMapping("modify")
    public Map<String, Long> modify(ProductDTO productDTO){

        log.info("modify....." + productDTO.getPno() + " "+productDTO.getPname());

        log.info(productDTO);

        if(productDTO.getFiles() != null && productDTO.getFiles().size() > 0){
            List<String> uploadFileNames = fileUploader.uploadFiles(productDTO.getFiles(), true);
            List<String> oldFileNames = productDTO.getImages();

            uploadFileNames.forEach(fname -> oldFileNames.add(fname));
        }

        log.info("After..");
        log.info(productDTO);

        productService.modify(productDTO);

        return Map.of("result",productDTO.getPno());
    }




    
}
