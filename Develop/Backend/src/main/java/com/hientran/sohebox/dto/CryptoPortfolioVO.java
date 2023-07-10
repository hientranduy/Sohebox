package com.hientran.sohebox.dto;

import java.util.Date;

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
public class CryptoPortfolioVO  {
	private Long id;
	private Date updatedDate;
	private UserVO user;
	private CryptoTokenConfigVO token;
	private String wallet;
	private String starname;
	private Double amtAvailable;
	private Double amtTotalDelegated;
	private Double amtTotalReward;
	private Double amtTotalUnbonding;
	private CryptoValidatorVO validator;
	private Date syncDate;
}