package com.cbox.kioskservice.api.order.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbox.kioskservice.api.order.dto.OrderItemDTO;
import com.cbox.kioskservice.api.order.dto.OrderItemListDTO;
import com.cbox.kioskservice.api.order.service.OrderService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/order")
public class OrderController {
    
    private final OrderService orderService;



    @PostMapping("/change")
    public List<OrderItemListDTO> changeOrder(@RequestBody OrderItemDTO itemDTO){

        log.info(itemDTO);

        if(itemDTO.getQty()<= 0){
            return orderService.remove(itemDTO.getOino());
        }

        return orderService.addOrModifiy(itemDTO);
    }

    @GetMapping("/items")
    public List<OrderItemListDTO> getOrderItems(Principal principal) {
        String id = principal.getName();

        log.info("___________________-");
        log.info("id : "+ id);

        return orderService.getOrderItems(id);
    }
    

    public List<OrderItemListDTO> removeFromOrder(@PathVariable("oino") Long oino){

        log.info("order item no :"+ oino);

        return orderService.remove(oino);
    }
}
