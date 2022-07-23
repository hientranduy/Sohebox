package com.hientran.sohebox.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true)
public class CryptoPortfolioHistoryVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Date timeStamp;

    private UserVO user;

    private CryptoTokenConfigVO token;

    private Double totalAvailable;

    private Double totalDelegated;

    private Double totalReward;

    private Double totalUnbonding;

    private Double totalIncrease;
}