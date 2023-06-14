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
@Table(name = "type_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_type", columnNames = { "typeClass", "typeCode" }) })
public class TypeTbl extends BaseTbl {
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

	@Column(name = "url")
	private String url;
}