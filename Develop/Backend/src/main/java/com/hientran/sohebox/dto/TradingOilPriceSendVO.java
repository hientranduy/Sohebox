package com.hientran.sohebox.dto;

import java.util.List;

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
public class TradingOilPriceSendVO extends BaseVO {
	private TradingSymbolItemVO symbolCL1;
	private TradingSymbolItemVO symbolCO1;
	private List<TradingHistoryItemVO> historyCL1;
	private List<TradingHistoryItemVO> historyCO1;
}