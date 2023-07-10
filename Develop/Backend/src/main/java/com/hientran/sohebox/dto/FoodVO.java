package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.FoodTypeTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class FoodVO extends BaseVO {
	private Long id;
	private String name;
	private String imageName;
	private String description;
	private String locationNote;
	private FoodTypeTbl type;
	private FoodTypeTbl category;
	private Boolean isFastFood;
	private byte[] recipe;
	private String recipeString;
	private String urlReference;

	// Extra fields
	private String imageFile;
	private String imageExtention;
}