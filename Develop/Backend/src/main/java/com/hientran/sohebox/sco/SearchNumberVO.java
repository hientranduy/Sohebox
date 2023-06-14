package com.hientran.sohebox.sco;

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
public class SearchNumberVO {
	private Double le;
	private Double ge;
	private Double eq;
	private Double notEq;
	private Double lt;
	private Double gt;
	private Double[] in;

	public SearchNumberVO(Double eq) {
		super();
		this.eq = eq;
	}
}
