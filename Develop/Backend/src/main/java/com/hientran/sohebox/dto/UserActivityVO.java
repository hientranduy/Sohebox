package com.hientran.sohebox.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.TypeTbl;
import com.hientran.sohebox.entity.UserTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActivityVO  {
	@JsonIgnore
	private Long id;

	private Date createdDate;

	private UserVO user;

	private UserTbl userTbl;

	private TypeTbl activity;
}