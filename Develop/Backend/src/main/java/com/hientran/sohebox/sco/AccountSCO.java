package com.hientran.sohebox.sco;

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
public class AccountSCO extends BaseSCO {
	private SearchNumberVO id;
	private SearchNumberVO user;
	private SearchNumberVO accountType;
	private SearchTextVO accountName;
	private SearchTextVO note;

	// Sub Field //
	private SearchTextVO userName;
	private SearchTextVO accountTypeName;
}
