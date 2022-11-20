package com.coffee.dto;

import com.coffee.constant.ItemSellStatus;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;


@Getter @Setter
public class ItemSearchDto {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";

}