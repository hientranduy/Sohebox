package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.TypeTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountVO {
	private Long id;
	private UserVO user;
	private TypeTbl accountType;
	private String accountName;
	private String mdp;
	private String note;

	///////////////
	// SUB FIELD //
	///////////////
	private String privateKey;
}