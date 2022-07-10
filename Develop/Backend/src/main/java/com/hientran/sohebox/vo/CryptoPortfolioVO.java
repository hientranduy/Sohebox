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
public class CryptoPortfolioVO extends BaseVO {

    private static final long serialVersionUID = 1L;

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

}