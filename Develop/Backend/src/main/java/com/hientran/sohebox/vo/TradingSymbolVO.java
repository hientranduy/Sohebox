package com.hientran.sohebox.vo;

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
public class TradingSymbolVO extends BaseVO {
	private String symbol;
	private String name;
	private CountryVO country;
	private TypeVO symbolType;
	private TypeVO zone;
	private String description;
}