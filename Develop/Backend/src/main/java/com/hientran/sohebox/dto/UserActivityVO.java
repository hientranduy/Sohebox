package com.hientran.sohebox.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.UserTbl;

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
public class UserActivityVO extends BaseVO {
	@JsonIgnore
	private Long id;

	private Date createdDate;

	private UserVO user;

	private UserTbl userTbl;

	private TypeVO activity;
}