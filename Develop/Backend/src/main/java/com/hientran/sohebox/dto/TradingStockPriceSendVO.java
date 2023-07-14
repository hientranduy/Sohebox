package com.hientran.sohebox.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TradingStockPriceSendVO {
	private List<TradingSymbolItemVO> america;
	private List<TradingSymbolItemVO> europe;
	private List<TradingSymbolItemVO> asia;
	private List<TradingSymbolItemVO> africa;
}