package com.hientran.sohebox.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class CryptoValidatorVO extends BaseVO {
	private Long id;
	private Date updatedDate;
	private String validatorAddress;
	private String validatorName;
	private String validatorWebsite;
	private Double commissionRate;
	private Double totalDeligated;
	private Date syncDate;
}