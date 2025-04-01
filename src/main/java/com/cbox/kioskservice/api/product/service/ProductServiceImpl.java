package com.cbox.kioskservice.api.product.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbox.kioskservice.api.common.file.FileUploader;
import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;
import com.cbox.kioskservice.api.product.domain.Product;
import com.cbox.kioskservice.api.product.domain.ProductImage;
import com.cbox.kioskservice.api.product.dto.ProductDTO;
import com.cbox.kioskservice.api.product.dto.ProductListDTO;
import com.cbox.kioskservice.api.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository repository;

    private final ModelMapper modelMapper;

    private final FileUploader fileUploader;



    @Override
    public ProductDTO readOne(Long pno) {
        Product result = repository.selectOne(pno);
        log.info(result);

        
        return modelMapper.map(result, ProductDTO.class);
    }

    @Override
    public Long register(ProductDTO productDTO) {
        log.info("register");
        
        Product product = Product.builder()
            .pname(productDTO.getPname())
            .price(productDTO.getPrice())
            .category(productDTO.getCategory())
            .inventory(productDTO.getInventory())
            .pdesc(productDTO.getDesc())
            .build();

        productDTO.getImages().forEach(fname -> product.addImage(fname));

        return repository.save(product).getPno();
        
    }

    @Override
    public void remove(Long pno) {
        
        Product product = repository.selectOne(pno);

        product.changeDel(false);

        repository.save(product);

        List<String> fileNames =
                product.getImages().stream().map(pi -> pi.getPfname()).collect(Collectors.toList());
        
        fileUploader.removeFiles(fileNames);
        
    }

    @Override
    public void modify(ProductDTO productDTO) {

        Optional<Product> result = repository.findById(productDTO.getPno());

        Product product = result.orElseThrow();

        product.changepname(productDTO.getPname());
        product.changePrice(productDTO.getPrice());
        product.changeSold(productDTO.isSoldout());
        product.ChangeCategory(productDTO.getCategory());
        product.changeInventory(product.getInventory());
        

        Set<String> oldFileNames = product.getImages().stream()
                                .map(ProductImage::getPfname)
                                .collect(Collectors.toSet());

        product.clearImages();

         // List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getPfname()).collect(Collectors.toList());

        // productDTO.getImages().forEach(fname -> product.addImage(fname));

        // List<String> newFiles = productDTO.getImages();
        // List<String> wantDeleteFiles = oldFileNames.stream()
        //         .filter(f -> newFiles.indexOf(f) == -1)
        //         .collect(Collectors.toList());

        Set<String> newFiles = new HashSet<>(productDTO.getImages());
        List<String> wantDeleteFiles = oldFileNames.stream()
                    .filter(f -> !newFiles.contains(f))
                    .collect(Collectors.toList());


        productDTO.getImages().forEach(product::addImage);

        repository.save(product);



        fileUploader.removeFiles(wantDeleteFiles);
        
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {
        

        return repository.list(pageRequestDTO);
    }

    @Override
    public PageResponseDTO<ProductListDTO> listCate(PageRequestDTO pageRequestDTO) {
        
        return repository.listWithCate(pageRequestDTO);
    }
    
    
}
