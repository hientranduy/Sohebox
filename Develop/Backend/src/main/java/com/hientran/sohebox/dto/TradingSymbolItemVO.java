package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hientran.sohebox.entity.CountryTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradingSymbolItemVO {
	@JsonProperty("Symbol")
	private String symbol;

	@JsonProperty("Ticker")
	private String ticker;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("Date")
	private String date;

	@JsonProperty("Type")
	private String type;

	@JsonProperty("state")
	private String state;

	@JsonProperty("Last")
	private Double last;

	@JsonProperty("DailyChange")
	private Double dailyChange;

	@JsonProperty("DailyPercentualChange")
	private Double dailyPercentualChange;

	@JsonProperty("WeeklyChange")
	private Double weeklyChange;

	@JsonProperty("WeeklyPercentualChange")
	private Double weeklyPercentualChange;

	@JsonProperty("MonthlyChange")
	private Double monthlyChange;

	@JsonProperty("MonthlyPercentualChange")
	private Double monthlyPercentualChange;

	@JsonProperty("YearlyChange")
	private Double yearlyChange;

	@JsonProperty("YearlyPercentualChange")
	private Double yearlyPercentualChange;

	@JsonProperty("LastUpdate")
	private String lastUpdate;

	// Other field
	private CountryTbl country;
}