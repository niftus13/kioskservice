package com.cbox.kioskservice.api.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbox.kioskservice.api.admin.domain.MemberRole;
import com.cbox.kioskservice.api.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

    @Query("SELECT o FROM Order o WHERE o.owner.id = :id")
    public Optional<Order> getOrderOfmember(@Param("id") String id);

    Optional<Order> findByOwnerId(String id);

    Optional<Order> findByOno(Long ono);

}
