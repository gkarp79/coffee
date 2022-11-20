package com.coffee.service;

import com.coffee.constant.OrderStatus;
import com.coffee.dto.*;
import com.coffee.repository.*;
import com.coffee.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    private final ItemImgRepository itemImgRepository;

    private final OrderItemRepository orderItemRepository;

    public Long order(OrderDto orderDto, String email) {

        Item item = itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }



    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            return false;
        }

        return true;
    }
    @Transactional(readOnly = true)
    public boolean validateOrderManager(Long orderId, String email) {
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        String savedMember = order.getOrderItems().get(0).getItem().getCreatedBy();
        System.out.println("savedMember = " + savedMember);
        System.out.println("curMember = " + curMember.getEmail());
        if (!StringUtils.equals(curMember.getEmail(), savedMember)) {
            return false;
        }

        return true;
    }
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email) {

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }

    public OrderHistDto orderDetail(String createBy, Long orderId) {
/*        List<OrderManageDto> orderManageDto = orderItemRepository._OrderDetail(createBy,orderId);
        for(OrderManageDto orderManageDto1 : orderManageDto){
            if(orderManageDto1.getId().equals(orderId)){
                System.out.println("orderManageDto1 = " + orderManageDto1);
                return orderManageDto1;
            }
        }

        return null;*/
        Order order = orderRepository.findOrderById(orderId);
        OrderHistDto orderHistDto = new OrderHistDto(order);
        List<OrderItem> orderItems = order.getOrderItems();

        for (OrderItem orderItem : orderItems) {
            ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                    (orderItem.getItem().getId(), "Y");
            OrderItemDto orderItemDto =
                    new OrderItemDto(orderItem, itemImg.getImgUrl());
            orderHistDto.addOrderItemDto(orderItemDto);
        }
        return orderHistDto;
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                        (orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    public void updateOrder(Long order_id, OrderStatus status) {
        Order order = orderRepository.findById(order_id)
                .orElseThrow(EntityNotFoundException::new);
        order.changeOrderStatus(status);
    }

    @Transactional(readOnly = true)
    public Page<OrderManageDto> getOrdersList(Pageable pageable, String createBy) {
        return orderItemRepository._OrderList(createBy, pageable);
    }


    @Transactional(readOnly = true)
    public Page<OrderManageDto> getOrderStatus(OrderStatus status, String createBy , Pageable pageable) {
        return orderItemRepository._OrderListStatus(status ,createBy, pageable);
    }


    @Transactional(readOnly = true)
    public Page<OrderManageDto> getOrders_C_List(Pageable pageable, String createBy) {
        return orderItemRepository._Order_C_List(createBy, pageable);
    }


    @Transactional(readOnly = true)
    public Page<OrderManageDto> getOrder_C_Status(OrderStatus status, String createBy , Pageable pageable) {
        return orderItemRepository._OrderList_C_Status(status ,createBy, pageable);
    }

}