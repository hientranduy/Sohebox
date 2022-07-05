package com.hientran.sohebox.vo;

import java.util.List;

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
public class CryptoPortfolioBankRewardDetailVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private List<CryptoPortfolioValidatorRewardVO> rewards;

    private List<CryptoPortfolioCoinVO> total;

}