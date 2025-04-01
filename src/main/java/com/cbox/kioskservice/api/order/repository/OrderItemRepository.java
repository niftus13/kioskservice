package com.cbox.kioskservice.api.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbox.kioskservice.api.admin.domain.MemberRole;
import com.cbox.kioskservice.api.order.domain.OrderItem;
import com.cbox.kioskservice.api.order.dto.OrderItemListDTO;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{



    @Query(
        "select"+
        " new com.cbox.kioskservice.api.order.dto.OrderItemListDTO(oi.oino, oi.qty, p.pno, p.pname, p.price, pi.pfname, o.regDate)"+
        " from"+
        " OrderItem oi inner join Order o on oi.order = o"+
        " left join Product p on oi.product = p"+
        " left join p.images pi"+
        " where"+
        " o.owner.id = :id and pi.ord = 0"+
        " order by oi desc"
    )
    public List<OrderItemListDTO> getItemsOfOrderDTOByid(@Param("id") String id);


    @Query(
        "select oi from OrderItem oi"+
        " inner join Order o on oi.order = o"+
        " where"+
        " o.owner.id = :id and oi.product.pno = :pno"
    )
    public OrderItem getItemOfPno(@Param("id") String id, @Param("pno") Long pno);


    @Query(
        "select o.ono from Order o"+
        " inner join OrderItem oi on oi.order = o"+
        " where oi.oino = :oino"
    )
    public Long getOrderFromItem(@Param("oino") Long oino);

    
    @Query(
        "select new com.cbox.kioskservice.api.order.dto.OrderItemListDTO(" +
        "oi.oino, oi.qty, p.pno, p.pname, p.price, pi.pfname, o.regDate) " +
        "from OrderItem oi " +
        "inner join Order o on oi.order = o " +
        "left join Product p on oi.product = p " +
        "left join p.images pi " +
        "where o.ono = :ono and pi.ord = 0 " +
        "order by oi.oino desc"
    )
    public List<OrderItemListDTO> getItemsOfOrderDTObyOrder(@Param("ono") Long ono);




    @Query(
    "select sum(p.price * oi.qty) " +
    "from OrderItem oi " +
    "inner join oi.order o " +
    "left join oi.product p " +
    "left join p.images pi " +
    "where o.ono = :ono and pi.ord = 0"
)
    public int getItemsAllPriceByOrder(@Param ("ono") Long ono);

}
