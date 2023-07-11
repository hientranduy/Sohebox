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
public class PageResultVO<T>  {
	private long totalPage;
	private long totalElement;
	private long currentPage;
	private long pageSize;
	private List<T> elements;
}