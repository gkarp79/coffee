package com.coffee.dto;

import com.coffee.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class OrderManageDto {
    private Long id;

    private String updateDate;

    private String itemNm;

    private String buyer;

    private int count;

    private int price;

    private String seller;

    private OrderStatus status;

    public OrderManageDto(Long id, LocalDateTime updateDate, String itemNm, String buyer, int count, int price, String seller, OrderStatus status) {
        this.id = id;
        this.updateDate = updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.itemNm = itemNm;
        this.buyer = buyer;
        this.count = count;
        this.price = price;
        this.seller = seller;
        this.status = status;
    }


}
