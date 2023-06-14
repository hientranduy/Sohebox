package com.hientran.sohebox.vo;

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
public class TradingStockPriceSendVO extends BaseVO {
	private List<TradingSymbolItemVO> america;
	private List<TradingSymbolItemVO> europe;
	private List<TradingSymbolItemVO> asia;
	private List<TradingSymbolItemVO> africa;
}