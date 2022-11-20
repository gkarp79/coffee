package com.coffee.repository;


import com.coffee.dto.ItemSearchDto;
import com.coffee.dto.MainItemDto;
import com.coffee.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable, String createBy);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}