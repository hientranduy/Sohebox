package com.hientran.sohebox.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "request_external_tbl")
public class RequestExternalTbl extends GenericTbl {
	@Column(name = "createdDate", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdDate;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_RequestExternalTbl_TypeTbl_requestType"))
	private TypeTbl requestType;

	@Column(name = "requestUrl", nullable = false, length = 500)
	private String requestUrl;

	@Column(name = "note")
	private String note;
}
