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
public class CryptoValidatorVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date updatedDate;

    private String validatorAddress;

    private String validatorName;

    private String validatorWebsite;

    private Double commissionRate;

    private Double totalDeligated;

    private Date syncDate;
}