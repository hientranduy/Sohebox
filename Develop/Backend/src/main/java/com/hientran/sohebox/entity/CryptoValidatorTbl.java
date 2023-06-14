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
@Table(name = "crypto_validator_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_crypto_validator", columnNames = { "validatorAddress" }) })
public class CryptoValidatorTbl extends BaseTbl {
	@Column(name = "validatorAddress", nullable = false)
	private String validatorAddress;

	@Column(name = "validatorName", nullable = false)
	private String validatorName;

	@Column(name = "validatorWebsite")
	private String validatorWebsite;

	@Column(name = "commissionRate", nullable = false)
	private Double commissionRate;

	@Column(name = "totalDeligated")
	private Double totalDeligated;

	@Column(name = "syncDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date syncDate;
}
