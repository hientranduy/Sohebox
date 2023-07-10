package com.hientran.sohebox.dto;

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
public class EnglishLearnRecordVO  {
	private Long id;
	private UserVO user;
	private EnglishVO english;
	private Long recordTimes;
	private Long learnedToday;
	private String note;
	private Date updatedDate;
}