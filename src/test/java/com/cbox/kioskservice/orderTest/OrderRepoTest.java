package com.cbox.kioskservice.orderTest;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.cbox.kioskservice.api.admin.domain.Member;
import com.cbox.kioskservice.api.order.domain.Order;
import com.cbox.kioskservice.api.order.domain.OrderItem;
import com.cbox.kioskservice.api.order.dto.OrderItemListDTO;
import com.cbox.kioskservice.api.order.repository.OrderItemRepository;
import com.cbox.kioskservice.api.order.repository.OrderRepository;
import com.cbox.kioskservice.api.product.domain.Product;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class OrderRepoTest {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderItemRepository orderItemRepository;


    @Transactional
    @Test
    @Commit
    public void testInsertByProduct(){

        log.info("_______________insert test1");

        String id = "user0@aaa.com";
        long pno = 5L;
        int qty = 3;

        OrderItem orderItem = orderItemRepository.getItemOfPno(id, pno);

        if(orderItem != null){
            orderItem.changeQty(qty);
            orderItemRepository.save(orderItem);
        }

        Optional<Order> result = orderRepository.getOrderOfmember(id);

        Order order = null;

        if(result.isEmpty()){
            log.info("Member Order is not exist");

            Member member = Member.builder().id(id).build();

            Order tempOrder = Order.builder().owner(member).build();

            order = orderRepository.save(tempOrder);
        }else{
            order = result.get();
        }
        log.info(order);

        if(orderItem == null){
            Product product = Product.builder().pno(pno).build();

            orderItem = OrderItem.builder().product(product).order(order).qty(qty).build();
        }

        orderItemRepository.save(orderItem);
    }


    @Test
    @Commit
    public void testUpdateByOnio(){

        Long oino = 2L;

        int qty = 4;

        Optional<OrderItem> result = orderItemRepository.findById(oino);

        OrderItem orderItem = result.orElseThrow();

        orderItem.changeQty(qty);

        orderItemRepository.save(orderItem);

    }

    @Test
    public void testListOfMember(){

        String id = "user0@aaa.com";

        List<OrderItemListDTO> orderItemList = orderItemRepository.getItemsOfOrderDTOByid(id);

        for(OrderItemListDTO dto : orderItemList){
            log.info(dto);
        }
        
    }

    @Test
    public void testDeleteThenList(){

        Long oino = 2L;

        Long ono = orderItemRepository.getOrderFromItem(oino);


        // orderItemRepository.deleteById(oino);

        // orderRepository.deleteById(ono);

        List<OrderItemListDTO> orderItemList = orderItemRepository.getItemsOfOrderDTObyOrder(ono);

        for(OrderItemListDTO dto : orderItemList){
            log.info(dto);
        }


    }

    @Test
    public void allDeleteTest(){

        Long oino = 2L;

        Long ono = orderItemRepository.getOrderFromItem(oino);

        orderItemRepository.deleteById(oino);

        orderRepository.deleteAll();
    }




    
}
