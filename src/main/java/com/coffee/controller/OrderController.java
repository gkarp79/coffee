package com.coffee.controller;


import com.coffee.constant.OrderStatus;
import com.coffee.dto.*;
import com.coffee.service.OrderService;
import groovy.transform.NamedParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto
            , BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderId;

        try {
            orderId = orderService.order(orderDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principal.getName(), pageable);

        model.addAttribute("orders", ordersHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderHist";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, Principal principal) {

        if (!orderService.validateOrder(orderId, principal.getName())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }


    // 주문 리스트
    @GetMapping(value = {"manager/orders", "manager/orders/{page}"})
    public String itemManage(@PathVariable("page") Optional<Integer> page, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<OrderManageDto> orders = orderService.getOrdersList(pageable, ((UserDetails) principal).getUsername());

        model.addAttribute("items", orders);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderMng";
    }

    //주문 상세 정보
    @GetMapping(value = "/manager/order/{itemId}")
    public String Orderdelt(@PathVariable("itemId") Long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderHistDto order = orderService.orderDetail(((UserDetails) principal).getUsername(), id);
        System.out.println(order);
        model.addAttribute("order", order);
        return "order/orderState";
    }

    // 주문 상황 업데이트
    @GetMapping(value = "/manager/order/{orderid}/update")
    public String updateOrder(@PathVariable("orderid") Long orderid, @RequestParam String status) {
        System.out.println("status : " + status);
        OrderStatus _status = OrderStatus.valueOf(status);
        orderService.updateOrder(orderid, _status);
        return "redirect:/manager/orders";
    }

    //주문 상황 네비게이션용
    @GetMapping(value = {"manager/orderstatus/{status}", "manager/orderstatus/{status}/{page}"})
    public String orderStatus(@PathVariable("status") String _status, @PathVariable("page") Optional<Integer> page, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        OrderStatus status = OrderStatus.valueOf(_status);
        Page<OrderManageDto> orders = orderService.getOrderStatus(status,((UserDetails) principal).getUsername() ,pageable );

        model.addAttribute("items", orders);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/orderMng";
    }

    // 교환 및 환불 처리 리스트
    @GetMapping(value = {"manager/refund", "manager/refund/{page}"})
    public String refundOrder(@PathVariable("page") Optional<Integer> page, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<OrderManageDto> orders = orderService.getOrders_C_List(pageable, ((UserDetails) principal).getUsername());

        model.addAttribute("items", orders);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/refundMng";
    }

    @GetMapping(value = {"manager/refundstatus/{status}", "manager/refundstatus/{status}/{page}"})
    public String refundStatus(@PathVariable("status") String _status, @PathVariable("page") Optional<Integer> page, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        OrderStatus status = OrderStatus.valueOf(_status);
        Page<OrderManageDto> orders = orderService.getOrder_C_Status(status,((UserDetails) principal).getUsername() ,pageable );

        model.addAttribute("items", orders);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/refundMng";
    }

    @GetMapping(value = "/manager/order/{itemId}")
    public String refunddelt(@PathVariable("itemId") Long id, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderHistDto order = orderService.orderDetail(((UserDetails) principal).getUsername(), id);
        System.out.println(order);
        model.addAttribute("order", order);
        return "order/orderState";
    }

    // 주문 상황 업데이트
    @GetMapping(value = "/manager/order/{orderid}/update")
    public String updaterefund(@PathVariable("orderid") Long orderid, @RequestParam String status) {
        System.out.println("status : " + status);
        OrderStatus _status = OrderStatus.valueOf(status);
        orderService.updateOrder(orderid, _status);
        return "redirect:/manager/orders";
    }

}