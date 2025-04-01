package com.cbox.kioskservice.api.order.service;

import java.util.List;

import com.cbox.kioskservice.api.order.dto.OrderItemDTO;
import com.cbox.kioskservice.api.order.dto.OrderItemListDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface OrderService {
    
    public List<OrderItemListDTO> addOrModifiy(OrderItemDTO orderItemDTO);

    public List<OrderItemListDTO> getOrderItems(String id);

    public List<OrderItemListDTO> remove(Long oino);

    public void removeOrder(Long oino);
}
