package com.hientran.sohebox.sco;

import java.util.Date;

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
public class SearchDateVO {
	private Date lt;
	private Date gt;
	private Date le;
	private Date ge;
	private Date eq;
	private Date[] in;

	public SearchDateVO(Date eq) {
		super();
		this.eq = eq;
	}
}
