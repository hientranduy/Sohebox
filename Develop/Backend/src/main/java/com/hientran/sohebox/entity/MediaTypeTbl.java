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
@Table(name = "media_type_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_media_type", columnNames = { "type_class", "type_code" }) })
public class MediaTypeTbl extends BaseTbl {
	@Column(name = "type_class", nullable = false)
	private String typeClass;

	@Column(name = "type_code", nullable = false)
	private String typeCode;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "description")
	private String description;

	@Column(name = "icon_url")
	private String iconUrl;
}