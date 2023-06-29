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
public class SearchTextVO {
	private String eq;
	private String like;
	private int likeMode = 0;
	private String[] in;

	public SearchTextVO(String eq) {
		super();
		this.eq = eq;
	}
}
