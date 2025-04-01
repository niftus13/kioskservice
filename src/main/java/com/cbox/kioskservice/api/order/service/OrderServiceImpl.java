package com.cbox.kioskservice.api.order.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cbox.kioskservice.api.admin.domain.Member;
import com.cbox.kioskservice.api.order.domain.Order;
import com.cbox.kioskservice.api.order.domain.OrderItem;
import com.cbox.kioskservice.api.order.dto.OrderItemDTO;
import com.cbox.kioskservice.api.order.dto.OrderItemListDTO;
import com.cbox.kioskservice.api.order.repository.OrderItemRepository;
import com.cbox.kioskservice.api.order.repository.OrderRepository;
import com.cbox.kioskservice.api.product.domain.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{


    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;
    
    
    @Override
    public List<OrderItemListDTO> addOrModifiy(OrderItemDTO orderItemDTO) {

        String id = orderItemDTO.getId();

        Long pno = orderItemDTO.getPno();

        int qty = orderItemDTO.getQty();

        Long oino = orderItemDTO.getOino();

        if(oino != null){ // 아이템 번호로 수량만 변경하는 경우

            Optional<OrderItem> orderItemResult = orderItemRepository.findById(oino);
            OrderItem orderItem = orderItemResult.orElseThrow();

            orderItem.changeQty(qty);

            orderItemRepository.save(orderItem);

            return getOrderItems(id);
        }
        
        // oino가 없는경우
        Order order = getOrder(id);

        OrderItem orderItem = null;

        //동일한 상품이 담긴적이 있는경우
        orderItem = orderItemRepository.getItemOfPno(id, pno);

        if(orderItem == null){
            Product product = Product.builder().pno(pno).build();
            orderItem = OrderItem.builder().product(product).order(order).qty(qty).build();
        }else{
            orderItem.changeQty(qty);
        }

        orderItemRepository.save(orderItem);

        return getOrderItems(id);

    }

    //새로운 장바구니를 생성 후 반환
    public Order getOrder(String id){
        Order order = null;

        Optional<Order> result = orderRepository.getOrderOfmember(id);

        if(result.isEmpty()){
            log.info("order of the member is not exist");
            Member member = Member.builder().id(id).build();
            Order tempOrder = Order.builder().owner(member).build();

            order = orderRepository.save(tempOrder);
        }else{
            order = result.get();
        }
        return order;
    }

    @Override
    public List<OrderItemListDTO> getOrderItems(String id) {
        
        return orderItemRepository.getItemsOfOrderDTOByid(id);
    }

    @Override
    public List<OrderItemListDTO> remove(Long oino) {
       
        Long ono = orderItemRepository.getOrderFromItem(oino);

        log.info("orderNum = "+ ono);

        orderItemRepository.deleteById(oino);

        return orderItemRepository.getItemsOfOrderDTObyOrder(ono);
    }

    @Override
    public void removeOrder(Long oino) {
        
        Long ono = orderItemRepository.getOrderFromItem(oino);

        if(ono == null){

        }
    }
    
}
