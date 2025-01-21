package com.resturent.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resturent.Dto.MenuItemDto;
import com.resturent.Services.MenuItemService;

@RestController
@RequestMapping("/api")
public class MenuItemController {
	
	@Autowired
	private MenuItemService menuItemService;

	
	
	//add item in manu
	@PostMapping("/item")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MenuItemDto> addItem(@RequestBody MenuItemDto menuItemDto){
		return new ResponseEntity<>(menuItemService.createItem(menuItemDto),HttpStatus.CREATED);
	}
	
	
	//get all items
	@GetMapping("/items")
	public ResponseEntity<List<MenuItemDto>> getAllItems(){
		return new ResponseEntity<>(menuItemService.menuItems(),HttpStatus.OK);

	}
	
	//item by id or name
	@GetMapping("/item")
	public ResponseEntity<MenuItemDto> itemByIdOrName(@RequestParam(required=false) Long itemId, @RequestParam(required = false) String itemName){
		return new ResponseEntity<>(menuItemService.itemByIdOrName(itemId, itemName.trim()),HttpStatus.OK);
	}
	
	//update item status for perticular id
	@PutMapping("/item/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> updateStatus(@PathVariable Long id,@RequestParam Boolean status){
		return new ResponseEntity<>(menuItemService.updateStatusById(id,status),HttpStatus.OK);
	}
	
	
	
}
