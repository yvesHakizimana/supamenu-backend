package com.supamenu.backend.menus.mappers;

import com.supamenu.backend.menus.MenuItem;
import com.supamenu.backend.menus.dtos.MenuItemDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MenuItemMapper {

   @Mapping(target = "category", expression = "java(menuItem.getCategory())")
   MenuItemDto toDto(MenuItem menuItem);

}
