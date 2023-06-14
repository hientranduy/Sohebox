package com.hientran.sohebox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food_type_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_food_type", columnNames = { "typeClass", "typeCode" }) })
public class FoodTypeTbl extends BaseTbl {
	@Column(name = "typeClass", nullable = false)
	private String typeClass;

	@Column(name = "typeCode", nullable = false)
	private String typeCode;

	@Column(name = "typeName")
	private String typeName;

	@Column(name = "description")
	private String description;

	@Column(name = "iconUrl")
	private String iconUrl;
}