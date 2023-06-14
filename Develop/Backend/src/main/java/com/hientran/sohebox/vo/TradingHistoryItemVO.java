package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class TradingHistoryItemVO extends BaseVO {
	@JsonProperty("Symbol")
	private String symbol;

	@JsonProperty("Date")
	private String date;

	@JsonProperty("Open")
	private Double open;

	@JsonProperty("High")
	private Double high;

	@JsonProperty("Low")
	private Double low;

	@JsonProperty("Close")
	private Double close;
}
