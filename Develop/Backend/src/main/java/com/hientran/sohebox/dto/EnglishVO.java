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
public class EnglishVO {
	private Long id;
	private String keyWord;
	private EnglishTypeTbl wordLevel;
	private EnglishTypeTbl category;
	private EnglishTypeTbl learnDay;
	private String imageName;
	private String imageFile;
	private String explanationEn;
	private String explanationVn;
	private String voiceUkFile;
	private String voiceUsFile;
	private EnglishTypeTbl vusGrade;

	// Other fields//
	private String imageExtention;
	private Long recordTimes;
}