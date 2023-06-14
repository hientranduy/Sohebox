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
public class TradingSymbolSCO extends BaseSCO {
	private SearchTextVO symbol;
	private SearchTextVO name;
	private SearchNumberVO country;
	private SearchNumberVO symbolType;
	private SearchNumberVO zone;
	private SearchTextVO description;
}
