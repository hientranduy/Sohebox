package com.hientran.sohebox.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.TypeTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestExternalVO  {
	private Long id;
	private Date createdDate;
	private TypeTbl requestType;
	private String requestUrl;
	private String note;
}