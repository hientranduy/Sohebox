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
		@UniqueConstraint(name = "UQ_crypto_token_config", columnNames = { "token_code" }) })
public class CryptoTokenConfigTbl extends BaseTbl {
	@Column(name = "token_code", nullable = false)
	private String tokenCode;

	@Column(name = "token_name")
	private String tokenName;

	@Column(name = "icon_url")
	private String iconUrl;

	@Column(name = "node_url")
	private String nodeUrl;

	@Column(name = "rpc_url")
	private String rpcUrl;

	@Column(name = "denom")
	private String denom;

	@Column(name = "decimal_exponent")
	private Long decimalExponent;

	@Column(name = "address_prefix")
	private String addressPrefix;

	@Column(name = "mintscan_prefix")
	private String mintscanPrefix;

	@Column(name = "deligate_url")
	private String deligateUrl;
}