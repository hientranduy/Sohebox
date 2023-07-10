package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.CountryTbl;
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
public class TradingSymbolVO {
	private String symbol;
	private String name;
	private CountryTbl country;
	private TypeTbl symbolType;
	private TypeTbl zone;
	private String description;
}