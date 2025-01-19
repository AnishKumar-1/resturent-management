package com.resturent.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resturent.Dto.MenuItemDto;
import com.resturent.ErrorsHandling.ItemNotFoundException;
import com.resturent.Modules.MenuItem;
import com.resturent.Repositories.MenuItemRepo;

@Service
public class MenuItemService {

	@Autowired
	private MenuItemRepo menuItemRepo;
	
	//crete/add item in item table
	public MenuItemDto createItem(MenuItemDto dto) {
		MenuItem data=convetToMenuItem(dto);
		return convertToMenuItemDto(menuItemRepo.save(data));
	}
	
	
    //get all items
	public List<MenuItemDto> menuItems(){
		return menuItemRepo.findAll().stream().filter(item->item.getAvailable())
		.map(menuItemObject->convertToMenuItemDto(menuItemObject))
		.collect(Collectors.toList());
	}
	
	
	//get item by id or name
	public MenuItemDto itemByIdOrName(Long itemId,String name) {
		
		Optional<MenuItem>menuItem=menuItemRepo.findByIdOrName(itemId, name);
	    if(menuItem.isEmpty()) {
	    	throw new ItemNotFoundException("Item not found");
	    }else {
	    	return convertToMenuItemDto(menuItem.get());
	    }
	}
	
	
	
	
	
	private MenuItem convetToMenuItem(MenuItemDto dto) {
		MenuItem menuItme=new MenuItem();
		menuItme.setName(dto.getName());
		menuItme.setPrice(dto.getPrice());
		menuItme.setImageUrl(dto.getImageUrl());
		menuItme.setCategory(dto.getCategory());
		menuItme.setAvailable(dto.getAvailable());
		return menuItme;
	}
	
	private MenuItemDto convertToMenuItemDto(MenuItem menuItem) {
		MenuItemDto menuItemDto=new MenuItemDto();
		menuItemDto.setId(menuItem.getId());
		menuItemDto.setName(menuItem.getName());
		menuItemDto.setPrice(menuItem.getPrice());
		menuItemDto.setImageUrl(menuItem.getImageUrl());
		menuItemDto.setCategory(menuItem.getCategory());
		menuItemDto.setAvailable(menuItem.getAvailable());
		return menuItemDto;
		
	}
}
