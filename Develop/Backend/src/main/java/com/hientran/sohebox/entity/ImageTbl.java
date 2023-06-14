package com.hientran.sohebox.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "image_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_image", columnNames = { "captureDate" }) })
public class ImageTbl extends BaseTbl {
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "captureDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date captureDate;
}
