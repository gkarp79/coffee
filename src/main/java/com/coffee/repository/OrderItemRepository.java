package com.coffee.repository;

import com.coffee.constant.OrderStatus;
import com.coffee.dto.OrderManageDto;
import com.coffee.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(value = "select" +
            " NEW com.coffee.dto.OrderManageDto(o.id, oi.order.updateTime, oi.item.itemNm, oi.order.createdBy, oi.count,oi.orderPrice, i.createdBy, o.orderStatus)" +
            " from OrderItem oi join Item i on oi.item.id = i.id join Order o on oi.order.id = o.id "
            + "where o.orderStatus <> com.coffee.constant.OrderStatus.EXCHANGE " +
            "and o.orderStatus <> com.coffee.constant.OrderStatus.EXCHANGE_DONE " +
            "and o.orderStatus <> com.coffee.constant.OrderStatus.REFUND " +
            "and o.orderStatus <> com.coffee.constant.OrderStatus.REFUND_DONE " +
            "ORDER BY o.id DESC")
    Page<OrderManageDto> _OrderList(String createBy, Pageable pageable);

    @Query(value = "select" +
            " NEW com.coffee.dto.OrderManageDto(o.id, oi.order.updateTime, oi.item.itemNm, oi.order.createdBy, oi.count,oi.orderPrice, i.createdBy, o.orderStatus)" +
            " from OrderItem oi join Item i on oi.item.id = i.id join Order o on oi.order.id = o.id" +
            " where o.orderStatus = ?1 ORDER BY o.id DESC")
    Page<OrderManageDto> _OrderListStatus(OrderStatus _status,String _createBy,Pageable _pageable);

    @Query(value = "select" +
            " NEW com.coffee.dto.OrderManageDto(o.id, oi.order.updateTime, oi.item.itemNm, oi.order.createdBy, oi.count,oi.orderPrice, i.createdBy, o.orderStatus)" +
            " from OrderItem oi join Item i on oi.item.id = i.id join Order o on oi.order.id = o.id " +
            "where o.orderStatus = com.coffee.constant.OrderStatus.EXCHANGE " +
            "or o.orderStatus = com.coffee.constant.OrderStatus.EXCHANGE_DONE " +
            "or o.orderStatus = com.coffee.constant.OrderStatus.REFUND " +
            "or o.orderStatus = com.coffee.constant.OrderStatus.REFUND_DONE" +
            " ORDER BY o.id DESC")
    Page<OrderManageDto> _Order_C_List(String createBy, Pageable pageable);

    @Query(value = "select" +
            " NEW com.coffee.dto.OrderManageDto(o.id, oi.order.updateTime, oi.item.itemNm, oi.order.createdBy, oi.count,oi.orderPrice, i.createdBy, o.orderStatus)" +
            " from OrderItem oi join Item i on oi.item.id = i.id join Order o on oi.order.id = o.id" +
            " where o.orderStatus = ?1" +
            " order by o.id desc")
    Page<OrderManageDto> _OrderList_C_Status(OrderStatus _status,String _createBy,Pageable _pageable);
}