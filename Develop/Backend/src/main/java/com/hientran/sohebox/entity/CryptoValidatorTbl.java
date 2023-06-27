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
		@UniqueConstraint(name = "UQ_crypto_validator", columnNames = { "validator_address" }) })
public class CryptoValidatorTbl extends BaseTbl {
	@Column(name = "validator_address", nullable = false)
	private String validatorAddress;

	@Column(name = "validator_name", nullable = false)
	private String validatorName;

	@Column(name = "validator_website")
	private String validatorWebsite;

	@Column(name = "commission_rate", nullable = false)
	private Double commissionRate;

	@Column(name = "total_deligated")
	private Double totalDeligated;

	@Column(name = "sync_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date syncDate;
}
