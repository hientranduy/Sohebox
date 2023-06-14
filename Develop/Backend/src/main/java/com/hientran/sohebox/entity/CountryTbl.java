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
@Table(name = "country_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_country", columnNames = { "name" }) })
public class CountryTbl extends GenericTbl {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "url")
	private String url;

	@Column(name = "note")
	private String note;
}
