package com.resturent.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDto {

	    private Long id;
	    private String name;
	    private Double price;
	    private String imageUrl;
	    private String category;
	    private Boolean available;
}
