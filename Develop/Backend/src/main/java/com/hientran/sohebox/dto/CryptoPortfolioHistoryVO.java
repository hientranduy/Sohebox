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
public class CryptoPortfolioHistoryVO {
	private Date timeStamp;
	private UserVO user;
	private CryptoTokenConfigVO token;
	private Double totalAvailable;
	private Double totalDelegated;
	private Double totalReward;
	private Double totalUnbonding;
	private Double totalIncrease;
	private Date lastSyncDate;
}