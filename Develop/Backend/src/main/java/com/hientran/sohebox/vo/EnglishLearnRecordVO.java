package com.hientran.sohebox.vo;

import java.util.Date;

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
public class EnglishLearnRecordVO extends BaseVO {
	private Long id;
	private UserVO user;
	private EnglishVO english;
	private Long recordTimes;
	private Long learnedToday;
	private String note;
	private Date updatedDate;
}