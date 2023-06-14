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
@Table(name = "role_tbl", uniqueConstraints = { @UniqueConstraint(name = "UQ_role", columnNames = { "roleName" }) })
public class RoleTbl extends BaseTbl {
	@Column(name = "roleName", nullable = false)
	private String roleName;

	@Column(name = "description")
	private String description;
}
