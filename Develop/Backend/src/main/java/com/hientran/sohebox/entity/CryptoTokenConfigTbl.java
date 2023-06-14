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
@Table(name = "crypto_token_config_tbl", uniqueConstraints = {
		@UniqueConstraint(name = "UQ_crypto_token_config", columnNames = { "tokenCode" }) })
public class CryptoTokenConfigTbl extends BaseTbl {
	@Column(name = "tokenCode", nullable = false)
	private String tokenCode;

	@Column(name = "tokenName")
	private String tokenName;

	@Column(name = "iconUrl")
	private String iconUrl;

	@Column(name = "nodeUrl")
	private String nodeUrl;

	@Column(name = "rpcUrl")
	private String rpcUrl;

	@Column(name = "denom")
	private String denom;

	@Column(name = "decimalExponent")
	private Long decimalExponent;

	@Column(name = "addressPrefix")
	private String addressPrefix;

	@Column(name = "mintscanPrefix")
	private String mintscanPrefix;

	@Column(name = "deligateUrl")
	private String deligateUrl;
}