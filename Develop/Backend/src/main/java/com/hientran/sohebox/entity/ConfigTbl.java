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
@Table(name = "config_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_config", columnNames = { "config_key" }) })
public class ConfigTbl extends BaseTbl {
	@Column(name = "config_key", nullable = false)
	private String configKey;

	@Column(name = "config_value")
	private String configValue;

	@Column(name = "description")
	private String description;
}