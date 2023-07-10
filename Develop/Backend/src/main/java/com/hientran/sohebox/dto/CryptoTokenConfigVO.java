package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoTokenConfigVO  {
	private Long id;
	private String tokenCode;
	private String tokenName;
	private String iconUrl;
	private String nodeUrl;
	private String rpcUrl;
	private String denom;
	private Long decimalExponent;
	private String addressPrefix;
	private String mintscanPrefix;
	private String deligateUrl;
}