package com.cbox.kioskservice.orderTest;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cbox.kioskservice.api.order.dto.OrderItemDTO;
import com.cbox.kioskservice.api.order.dto.OrderItemListDTO;
import com.cbox.kioskservice.api.order.service.OrderService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;


    @Test
    public void orderReadOne(){

        String id = "user0@aaa.com";

        List<OrderItemListDTO> results = orderService.getOrderItems(id);

        log.info(results);


    }





    
}
