package com.cbox.kioskservice.api.product.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cbox.kioskservice.api.product.domain.Product;
import com.cbox.kioskservice.api.product.repository.search.ProductSearch;


public interface ProductRepository extends JpaRepository<Product,Long>, ProductSearch{
    
    @EntityGraph(attributePaths = "images")
    @Query("select p from Product p where p.delFlag = false and p.pno = :pno")
    Product selectOne(@Param("pno")Long pno);

    @Modifying
    @Query("update Product p set p.delFlag = :flag where p.pno = :pno")
    void updateToDelete(@Param("pno")Long pno, @Param("flag") boolean flag);
    
    
}
