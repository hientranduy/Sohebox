package com.hientran.sohebox.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.CryptoTokenConfigTbl;
import com.hientran.sohebox.entity.CryptoValidatorTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoPortfolioVO {
	private Long id;
	private Date updatedDate;
	private UserVO user;
	private CryptoTokenConfigTbl token;
	private String wallet;
	private String starname;
	private Double amtAvailable;
	private Double amtTotalDelegated;
	private Double amtTotalReward;
	private Double amtTotalUnbonding;
	private CryptoValidatorTbl validator;
	private Date syncDate;
}