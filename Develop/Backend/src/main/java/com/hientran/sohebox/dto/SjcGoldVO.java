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
public class SjcGoldVO {
	private String title;
	private String url;
	private String updated;
	private String unit;
	private List<SjcGoldCityVO> city;
}