package com.cbox.kioskservice.api.product.repository.search;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.cbox.kioskservice.api.common.pageDTO.PageRequestDTO;
import com.cbox.kioskservice.api.common.pageDTO.PageResponseDTO;
import com.cbox.kioskservice.api.product.domain.Product;
import com.cbox.kioskservice.api.product.domain.QProduct;
import com.cbox.kioskservice.api.product.domain.QProductImage;
import com.cbox.kioskservice.api.product.dto.ProductListDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch{
    
    
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {
        
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.images, productImage);

        query.where(productImage.ord.eq(0));

        int pageNum = pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1 ;
        Pageable pageable =
            PageRequest.of(pageNum, pageRequestDTO.getSize(),
            Sort.by("pno").ascending());
        
        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<ProductListDTO> dtoQuery = 
            query.select(
                Projections.bean(ProductListDTO.class, 
                product.pno,
                product.product_name,
                product.inventory,
                product.price,
                product.pdesc,
                productImage.pfname)
            );

        List<ProductListDTO> dtoList = dtoQuery.fetch();

        Long totalCount = dtoQuery.fetchCount();

    
        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<ProductListDTO> listWithCate(PageRequestDTO pageRequestDTO) {
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.images, productImage);
        query.where(productImage.ord.eq(0));
        query.where(product.delFlag.eq(Boolean.FALSE));

        int pageNum = pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1;
        Pageable pageable =
            PageRequest.of( pageNum, pageRequestDTO.getSize(),
            Sort.by("pno").ascending() );

        String keyword = pageRequestDTO.getKeyword();
        String searchType = pageRequestDTO.getType();

        if(keyword != null && searchType != null){

            String[] searchArr = searchType.split("");

            BooleanBuilder searchBuilder = new BooleanBuilder();

            for(String type : searchArr){

                switch(type){
                    case "b" -> searchBuilder.or(product.product_name.contains(keyword));
                    case "c" -> searchBuilder.or(product.pdesc.contains(keyword));
                }

            }
            query.where(searchBuilder);
        }


        this.getQuerydsl().applyPagination(pageable, query);

        query.groupBy(product);

        JPQLQuery<ProductListDTO> dtoQuery =
                query.select(
                        Projections.bean(ProductListDTO.class,
                                product.pno,
                                product.product_name,
                                product.price,
                                product.inventory,
                                product.category,
                                product.soldout,
                                product.delFlag,
                                productImage.pfname.min().as("pfname"),
                                productImage.UUID.min().as("UUID")
                        )
                );
        List<ProductListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }

    @Override
    public PageResponseDTO<ProductListDTO> listWithSearchCate(PageRequestDTO pageRequestDTO) {
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> query = from(product);
        query.leftJoin(product.images, productImage);
        query.where(productImage.ord.eq(0));
        query.where(product.delFlag.eq(Boolean.FALSE));

        int pageNum = pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1;
        Pageable pageable =
            PageRequest.of( pageNum, pageRequestDTO.getSize(),
            Sort.by("pno").ascending() );

        String keyword = pageRequestDTO.getKeyword();
        String searchType = pageRequestDTO.getType();

        if(keyword != null && searchType != null){

            String[] searchArr = searchType.split("");

            BooleanBuilder searchBuilder = new BooleanBuilder();

            for(String type : searchArr){

                switch(type){
                    case "b" -> searchBuilder.or(product.product_name.contains(keyword));
                    case "c" -> searchBuilder.or(product.pdesc.contains(keyword));
                }

            }
            query.where(searchBuilder);
        }


        this.getQuerydsl().applyPagination(pageable, query);

        query.groupBy(product);

        JPQLQuery<ProductListDTO> dtoQuery =
                query.select(
                        Projections.bean(ProductListDTO.class,
                                product.pno,
                                product.product_name,
                                product.inventory,
                                product.price,
                                productImage.pfname.min().as("pfname"),
                                productImage.UUID.min().as("UUID")
                        )
                );
        List<ProductListDTO> dtoList = dtoQuery.fetch();

        long totalCount = dtoQuery.fetchCount();

        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }

    
    
}
