package com.hientran.sohebox.vo;

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
public class CryptoPortfolioCoinVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private String denom;

    private Double amount;

}