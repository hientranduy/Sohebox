package com.hientran.sohebox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hientran.sohebox.entity.EnglishTypeTbl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnglishUserGradeVO  {
	private Long id;
	private UserVO user;
	private EnglishTypeTbl vusGrade;
	private EnglishTypeTbl learnDay;
}